package com.story.concho.controller.web.open;

import com.story.concho.model.domain.User;
import com.story.concho.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {
    private final UserService userService;
    // 생성자 의존성 주입
    @Autowired
    public LoginController(UserService userService){
        this.userService = userService;
    }

    // 로그인 폼 주기
    @GetMapping("/user/log-in-form")
    public String getLogIn(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "pages/open/logIn";
    }
    // 로그인 받기
    @PostMapping("/user/log-in")
    public String postUserLogIn(HttpSession session, @ModelAttribute User user, RedirectAttributes redirectAttributes){
        String email = user.getEmail();
        String pw = user.getPw();
        boolean result = userService.logInCheckOk(email, pw);
        if(result){
            session.setAttribute("email", email);
            return "redirect:/user/home";
        }else{
            redirectAttributes.addFlashAttribute("message", "로그인 실패!");
            return "redirect:/user/log-in-form";
        }
    }

    @GetMapping("/user/log-out")
    public String getLogOut(HttpSession session, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", "로그아웃 성공...");
        session.invalidate();
        return "redirect:/home";
    }
}
