package com.pkiks1.passwordmanager.controllers;

import com.pkiks1.passwordmanager.domain.UserEntity;
import com.pkiks1.passwordmanager.security.PasswordManagerUser;
import com.pkiks1.passwordmanager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.context.SecurityContextHolder;
@Controller
@RequestMapping("/password-generator")
public class PasswordGeneratorController {
    @GetMapping
    public String getPassGenerator(Model model) {
        Object userObject = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userObject instanceof PasswordManagerUser) {
            model.addAttribute("loggedIn", true);
        }

        return "password-generator";
    }
}
