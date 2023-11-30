package com.mountblue.spring.blogApplication.controller;

import com.mountblue.spring.blogApplication.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showMyLoginPage() {
        return "login-page";
    }

    @RequestMapping("/register")
    public String register() {
        return "register-page";
    }

    @PostMapping("/adduser")
    public String addUser(@ModelAttribute("username") String username,
                          @ModelAttribute("email") String email,
                          @ModelAttribute("password") String password) {
        if(userService.addUser(username.trim(), email.trim(), password.trim()))
            return "redirect:/login";
        else
            return "redirect:/register?error";
    }
}
