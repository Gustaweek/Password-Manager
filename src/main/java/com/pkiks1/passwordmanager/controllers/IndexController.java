package com.pkiks1.passwordmanager.controllers;

import com.pkiks1.passwordmanager.security.PasswordManagerUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping({"", "/", "index", "index.html"})
    public String index(Model model) {
        Object userObject = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userObject instanceof PasswordManagerUser) {
            model.addAttribute("loggedIn", true);
        }

        return "index";
    }
}
