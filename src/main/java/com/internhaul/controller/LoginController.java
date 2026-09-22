package com.internhaul.controller;

import com.internhaul.dto.SignupRequest;
import com.internhaul.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/signup")
    public String signupPage(Model model) {

        model.addAttribute(
                "signupRequest",
                new SignupRequest()
        );

        return "signup";
    }

    @PostMapping("/signup")
    public String signup(
            @ModelAttribute SignupRequest request,
            Model model) {

        try {

            userService.registerManager(request);

            return "redirect:/login?registered=true";

        } catch (RuntimeException e) {

            model.addAttribute(
                    "error",
                    e.getMessage()
            );

            model.addAttribute(
                    "signupRequest",
                    request
            );

            return "signup";
        }
    }
}