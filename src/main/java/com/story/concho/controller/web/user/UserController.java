package com.story.concho.controller.web.user;

import com.story.concho.service.ImgService;
import com.story.concho.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// Controller 는 html 뷰 리턴
@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final ImgService imgService;
    // 생성자 의존성 주입
    @Autowired
    public UserController(UserService userService, ImgService imgService){
        this.userService = userService;
        this.imgService = imgService;
    }
    public static boolean sessionCheck(HttpSession session){
        return (session.getAttribute("email") == null);
    }

    @GetMapping("/forumUpload")
    public String getFoumUpload(){
        return "pages/user/post";
    }
    @GetMapping("/map")
    public String getMap(Model model, HttpSession session, RedirectAttributes redirectAttributes){
        if(sessionCheck(session)){
            redirectAttributes.addFlashAttribute("message", "세션이 만료되었습니다.");
            return  "redirect:/user/log-in-form";
        }
        String email = session.getAttribute("email").toString();

        // 받아온 JavaScript 코드를 모델에 추가합니다.
        model.addAttribute("proxyMapJs", userService.getMapJsString());
        // 받아온 jsonString 형태의 이미지 정보를 추가
        //model.addAttribute("latitudeList", imgService.getImagesLatitudeJson(email));
        // 받아온 jsonString 형태의 이미지 정보를 추가
        //model.addAttribute("longitudeList", imgService.getImagesLongitudeJson(email));

        // locationList
        model.addAttribute("imgList", imgService.getImagesJson(email));
        return "pages/user/map";
    }
    @GetMapping("/post")
    public String getPost(){
        return "pages/user/post";
    }

    // 세션 확인 후 회원 홈페이지 주기
    @GetMapping("/home")
    public String getUserHome(HttpSession session, Model model, RedirectAttributes redirectAttributes){
        if(sessionCheck(session)){
            redirectAttributes.addFlashAttribute("message", "세션이 만료되었습니다.");
            return  "redirect:/user/log-in-form";
        }

        String email = session.getAttribute("email").toString();
        String nickName = userService.getNickNameByEmail(email);
        model.addAttribute("nick_name", nickName);

        return "pages/user/homeUser";
    }
}
