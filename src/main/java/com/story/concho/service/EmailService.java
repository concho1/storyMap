package com.story.concho.service;

import com.story.concho.model.domain.Config;
import com.story.concho.model.domain.Token;
import com.story.concho.model.repository.ConfigRepository;
import com.story.concho.model.repository.TokenRepository;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final TokenRepository tokenRepository;
    private final ConfigRepository configRepository;
    private String baseDomain;
    @Value("${my.email}")
    private String emailSender;
    private static int token;  // 랜덤 인증 코드

    public EmailService(JavaMailSender javaMailSender, TokenRepository tokenRepository, ConfigRepository configRepository) {
        this.javaMailSender = javaMailSender;
        this.tokenRepository = tokenRepository;
        this.configRepository = configRepository;
    }
    public boolean checkTokenAndEmail(int token, String email){
        Optional<Token> tokenObj = tokenRepository.findByTokenEmailAndTokenValue(email, token);
        if(tokenObj.isPresent()){
            Token tmToken = tokenObj.get();
            LocalDateTime dateTime = tmToken.getTokenDate().toLocalDateTime();
            LocalDateTime now = LocalDateTime.now(); //날짜1
            //두 시간 차이를 분으로 환산
            LocalTime start = dateTime.toLocalTime();
            LocalTime end = now.toLocalTime();
            Duration diff = Duration.between(start, end);
            long diffMin = diff.toMinutes();
            if(diffMin > 15){
                System.out.println("토큰 시간 만료");
                return false;
            }else{
                System.out.println("토큰 인증 완료");
                tmToken.setTokenFlag(1);
                tokenRepository.save(tmToken);
                return true;
            }

        }else{
            return false;
        }
    }
    // 실제 메일 전송
    public void sendEmail(String mail) {
        // 메일 전송에 필요한 정보 설정
        MimeMessage message = createMail(mail);

        // 토큰 DB 저장
        Token tokenObj = new Token(mail, token, Timestamp.valueOf(LocalDateTime.now()), 0);
        tokenRepository.save(tokenObj);

        // 실제 메일 전송
        javaMailSender.send(message);

    }
    // 랜덤 인증 코드 생성
    public static void createNumber() {
        int min = 100000;
        int max = 999999;
        token = (int) ((Math.random() * (max - min)) + min);
    }
    // 메일 양식 작성
    public MimeMessage createMail(String mail){
        Optional<Config> configOp = configRepository.findById("domain");
        configOp.ifPresent(
                config -> this.baseDomain = config.getValue()
        );
        createNumber();  // 인증 코드 생성
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(emailSender);   // 보내는 이메일
            message.setRecipients(MimeMessage.RecipientType.TO, mail); // 보낼 이메일 설정
            message.setSubject("[지도위의 스토리] 회원가입을 위한 이메일 인증");  // 제목 설정
            String body = String.format(
                """
                <h1>안녕하세요.</h1>
                <h1>[지도위의 스토리] 입니다.</h1>
                <h3>회원가입을 위한 요청하신 인증 url 입니다.</h3><br>
                <a href='%s/user/email-token-check?token=%s&email=%s'><h2>회원가입 인증 링크입니다.</h2></a>
                <br>
                <h3>감사합니다.</h3>
                """, baseDomain, token, mail);
            message.setText(body,"UTF-8", "html");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }


    public boolean checkTokenFlagByEmail(String email){
        Optional<Token> tokenOptional = tokenRepository.findByTokenEmail(email);
        if(tokenOptional.isPresent()){
            Token token = tokenOptional.get();
            return (token.getTokenFlag() == 1);

        }else{
            return false;
        }
    }
}
