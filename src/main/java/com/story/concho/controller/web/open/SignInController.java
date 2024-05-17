package com.story.concho.controller.web.open;

import com.story.concho.model.domain.User;
import com.story.concho.service.EmailService;
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
public class SignInController {
    private final UserService userService;
    private final EmailService emailService;
    // 생성자 의존성 주입
    @Autowired
    public SignInController(UserService userService, EmailService emailService){
        this.userService = userService;
        this.emailService = emailService;
    }

    // 회원가입 폼 주기
    @GetMapping("user/sign-up-form")
    public String showSignUpUserForm(Model model){
        model.addAttribute("user", new User());
        return "pages/open/signUp";
    }
    // 회원가입 받기
    @PostMapping("user/sign-up")
    public String SignUpUser(HttpSession session, @ModelAttribute User user, RedirectAttributes redirectAttributes){
        boolean result = userService.createUser(user);
        if(result){
            session.setAttribute("email", user.getEmail());
            redirectAttributes.addFlashAttribute("message", "회원가입 성공!");
            return "redirect:/user/home";
        }else{
            System.out.println("등록 실패");
            redirectAttributes.addFlashAttribute("message", "회원가입 실패.");
            return "redirect:/user/sign-up-form";
        }
    }
}
