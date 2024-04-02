package com.story.concho.controller.web.open;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AController {
    @GetMapping("/")
    public String getIndex(){
        return "index";
    }
    @GetMapping("/home")
    public String getHome(){
        return "pages/open/home";
    }

}
/*

 */