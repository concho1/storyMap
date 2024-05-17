package com.story.concho.controller.web.email;

import com.story.concho.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/user")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/email-token-check")
    public String tokenCheck(@RequestParam("token") int tokenValue, @RequestParam("email") String email){
        System.out.println(tokenValue);
        System.out.println(email);
        boolean result = emailService.checkTokenAndEmail(tokenValue, email);
        if(result){
            return "pages/tokenCheck/tokenCheckPage";
        }else{
            return "pages/tokenCheck/falsePage";
        }
    }
}
