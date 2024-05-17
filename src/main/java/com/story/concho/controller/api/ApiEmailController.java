package com.story.concho.controller.api;

import com.story.concho.service.EmailService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/user")
public class ApiEmailController {

    private final EmailService emailService;

    public ApiEmailController(EmailService emailService) {
        this.emailService = emailService;
    }


    // 토큰 이메일 보내기
    @PostMapping("/email-send")
    public Boolean emailSend(@RequestParam("email") String email){
        emailService.sendEmail(email);
        return true;
    }

    @PostMapping("/token-check")
    public Boolean emailTokenCheck(@RequestParam("email") String email){
        return emailService.checkTokenFlagByEmail(email);
    }

}
