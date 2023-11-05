package com.joe.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@Controller
public class LoginController {


    @GetMapping("/loginAuthPage")
    public String showLoginPage(){
        return "login-authPage";
    }

    @GetMapping("/accessDenied")
    public String accessDenied(){
        return "access-denied";
    }

}
