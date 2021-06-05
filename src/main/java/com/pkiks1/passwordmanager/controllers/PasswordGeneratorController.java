package com.pkiks1.passwordmanager.controllers;

import com.pkiks1.passwordmanager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/password-generator")
public class PasswordGeneratorController {
    @GetMapping
    public String getPassGenerator(Model model) {
        return "password-generator";
    }
}
