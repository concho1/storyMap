package com.story.concho.controller.api;

import com.story.concho.service.ImgService;
import com.story.concho.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/user")
public class ApiUserController {

    private final UserService userService;
    private final ImgService imgService;

    // 생성자 의존성 주입
    @Autowired
    public ApiUserController(UserService userService, ImgService imgService){
        this.userService = userService;
        this.imgService = imgService;
    }

    @PostMapping("/delete-img")
    public Boolean deleteImg(@RequestParam("id") int id, HttpSession session){
        if(session.getAttribute("email") == null) {
            if(!userService.emailCheckOk((String) session.getAttribute("email"))){
                System.out.println("세션인증 실패");
                return false;
            }
        }
        imgService.deleteImg(id);
        return true;
    }

    // 이메일 중복 확인 api
    @PostMapping("/email-duplication")
    public Map<String, Boolean> checkEmail(@RequestBody Map<String, String> jsonMap){
        boolean result;
        // json 데이터의 key-value 쌍을 java 의 Map 인터페이스로 받는다.
        String email = jsonMap.get("email");
        result = !userService.emailCheckOk(email);

        // 다시 key-value 쌍으로 결과를 key: result 에 담아 return
        HashMap<String, Boolean> resultJsonMap = new HashMap<>();
        resultJsonMap.put("result", result);
        return resultJsonMap;
    }

    // 닉네임 중복 확인 api
    @CrossOrigin(origins = "*")
    @PostMapping("/nickname-duplication")
    public Boolean checkEmail(@RequestParam("nickname") String nickName){
        return !userService.nickNameCheckOk(nickName);
    }

    @PostMapping("/upload-img")
    public Map<String, String> singleFileUpload(@RequestParam("file") MultipartFile file, HttpSession session){
        System.out.println("세션인증 준비" + file.getOriginalFilename());
        var resultJsonMap = new HashMap<String, String>();

        if(session.getAttribute("email") == null) {
            resultJsonMap.put("result", "sessionFalse");
            return resultJsonMap;
        }
        System.out.println("세션인증 성공");
        String email = (String)session.getAttribute("email");

        if(file.isEmpty()){
            resultJsonMap.put("result", "fileEmpty");
        }else{
            String[] uploadResult = imgService.tryImgUpload(file, email);
            if(uploadResult.length == 3){
                if(uploadResult[2].equals("imgMax")){
                    resultJsonMap.put("result", "imgMax");
                    return resultJsonMap;
                }
            }
            if(uploadResult[0].equals("true")){
                resultJsonMap.put("path", uploadResult[1]);
                resultJsonMap.put("result", "true");
                resultJsonMap.put("fileKey", uploadResult[2]);

            } else if (uploadResult[0].equals("noGPS")) {
                resultJsonMap.put("path", uploadResult[1]);
                resultJsonMap.put("result", "noGPS");
                resultJsonMap.put("fileKey", uploadResult[2]);
            } else{
                resultJsonMap.put("result", "false");
            }
        }
        System.out.println("결과 리턴");
        return resultJsonMap;
    }
}
