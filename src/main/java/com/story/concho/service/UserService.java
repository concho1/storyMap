package com.story.concho.service;
import com.story.concho.model.domain.User;
import com.story.concho.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.time.LocalDate;
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
    // 의존성 주입
    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
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

    public boolean createUser(User user){
        boolean createUserResult = false;
        // 폴더 이름을 이메일에서 유효하지 않은 문자를 대체하여 생성
        String folderName = user.getEmail().replaceAll("[^a-zA-Z0-9.-]", "_");
        // 최종 경로
        String path = basePath + folderName;

        File folder = new File(path);
        // 혹시 폴더가 없으면 생성
        if(!folder.exists()){
            if(folder.mkdirs()){// 성공시 true 반환
                System.out.println("user 폴더 제작 완료 + user 추가 완료.");
                createUserResult = true;
            }else{
                System.out.println("폴더 제작 실패");
                return false;
            }
        }else{
            createUserResult = true;
        }

        // 현재 날짜를 가져옵니다.
        LocalDate now = LocalDate.now();
        // 원하는 날짜 형식을 정의합니다. 예: "yyyy-MM-dd"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // 날짜를 String으로 포맷합니다.
        String formattedDate = now.format(formatter);

        user.setFolderId(folder.getAbsolutePath());
        user.setSignupDate(formattedDate);

        user.setEmail(user.getEmail().trim());
        user.setPw(user.getPw().trim());

        userRepository.save(user);
        return createUserResult;
    }


}
