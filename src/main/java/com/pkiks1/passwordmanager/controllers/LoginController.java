package com.pkiks1.passwordmanager.controllers;

import com.pkiks1.passwordmanager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getLoginPage(Model model) {
        return "login";
    }

    /*
    @PostMapping
    public String logIn(@RequestParam(name = "login") String login, @RequestParam(name = "password") char[] password, Model model) {
        if (userService.isCredentialsCorrect(login, password)) {
            model.addAttribute("login", login);
            return "dashboard";
        }
        model.addAttribute("loginFailed", true);
        return getLoginPage(model);
    }
    */
}
