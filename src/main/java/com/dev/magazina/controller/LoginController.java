package com.dev.magazina.controller;

import com.dev.magazina.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginForm(Model model, HttpServletRequest request) {
        model.addAttribute("user", new User());
        return "login";
    }
}
