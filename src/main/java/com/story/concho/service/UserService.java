package com.story.concho.service;
import com.story.concho.model.domain.User;
import com.story.concho.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
    기본 CRUD 연산 메서드
    save(S entity): 주어진 엔티티를 저장합니다. 새로운 엔티티의 추가 또는 기존 엔티티의 업데이트에 사용됩니다.
    findById(ID id): 주어진 ID에 해당하는 엔티티의 인스턴스를 검색합니다. 결과는 Optional<T>로 반환됩니다.
    existsById(ID id): 주어진 ID를 가진 엔티티의 존재 여부를 반환합니다.
    findAll(): 모든 엔티티를 리스트로 반환합니다.
    findAllById(Iterable<ID> ids): 주어진 ID 컬렉션에 해당하는 엔티티들을 검색합니다.
    count(): 저장소에 있는 엔티티의 총 개수를 반환합니다.
    deleteById(ID id): 주어진 ID를 가진 엔티티를 삭제합니다.
    delete(T entity): 주어진 엔티티를 삭제합니다.
    deleteAll(Iterable<? extends T> entities): 주어진 엔티티 컬렉션을 삭제합니다.
    deleteAll(): 저장소의 모든 엔티티를 삭제합니다.
 */
@Service
public class UserService {
    @Value("${external.api.key}")
    private String apiKey;

    @Value("${external.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    // properties 로 저장 경로 지정(쉽게 수정 가능)
    @Value("${file.base}")
    private String basePath;

    private final UserRepository userRepository;
    private final EmailService emailService;
    // 의존성 주입
    @Autowired
    public UserService(UserRepository userRepository, EmailService emailService){
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    // email-pw 체크
    public boolean logInCheckOk(String email, String pw){
        email = email.trim();
        pw = pw.trim();
        return userRepository.existsByEmailAndPw(email, pw);
    }
    // email  체크
    public boolean emailCheckOk(String email){
        boolean emailCheckResult = userRepository.existsById(email);
        return emailCheckResult;
    }
    public String getNickNameByEmail(String email){
        String nickName = userRepository.findNicknameByEmail(email);
        return nickName;
    }

    public String getMapJsString(){
        // js 파일에 key 를 넣으면 보안 위험이 있기 때문에 서버에서 대신 인증받고 파일에 추가한 뒤 주기
        String urlWithKey = apiUrl + "?consumer_key=" + apiKey;
        String proxyMapJs = restTemplate.getForEntity(urlWithKey, String.class).getBody();
        return proxyMapJs;
    }

    public boolean nickNameCheckOk(String nickName){
        return userRepository.existsByNickname(nickName);
    }

    public boolean createUser(User user){
        boolean createUserResult = true;

        // 이메일 인증(토큰) 인증을 거친 회원가입인지 확인하는 과정
        if(!emailService.checkTokenFlagByEmail(user.getEmail())){
            return false;
        }

        user.setSignupDate(Timestamp.valueOf(LocalDateTime.now()));
        user.setEmail(user.getEmail().trim());
        user.setPw(user.getPw().trim());
        user.setImgCnt(0);
        user.setImgCntMax(100);
        userRepository.save(user);

        return createUserResult;
    }


}
