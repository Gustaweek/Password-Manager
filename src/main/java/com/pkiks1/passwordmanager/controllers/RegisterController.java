package com.pkiks1.passwordmanager.controllers;

import com.pkiks1.passwordmanager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.security.auth.login.CredentialException;

@Controller
@RequestMapping("register")
public class RegisterController {

    private final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getRegisterPage(Model model) {
        return "register";
    }

    @PostMapping
    public String registerUser(@RequestParam(name = "login") String login,
                               @RequestParam(name = "firstPassword") char[] firstPassword,
                               @RequestParam(name = "secondPassword") char[] secondPassword,
                               Model model) {

        try {
            if (userService.registerUser(login, firstPassword, secondPassword)) {
                model.addAttribute("discoverFeatures", true);
                return "dashboard";
            }
        } catch (CredentialException e) {
            model.addAttribute("incorrectData", true);
            return "register";
        } finally {
            model.addAttribute("login", login);
        }

        model.addAttribute("loginExistsError", true);
        return "register";
    }
}
