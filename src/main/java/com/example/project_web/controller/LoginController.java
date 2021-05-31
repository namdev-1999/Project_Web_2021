package com.example.project_web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping(value = "/login")
    public String login(Model model) {
        return "admin_login";
    }

    @GetMapping(value = "/404")
    public String error() {
        return "404";
    }

    @GetMapping(value = "/logout")
    public String logout() {
        return "admin_login";
    }
}
