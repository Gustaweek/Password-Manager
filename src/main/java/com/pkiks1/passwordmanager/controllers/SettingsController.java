package com.pkiks1.passwordmanager.controllers;

import com.pkiks1.passwordmanager.security.PasswordManagerUser;
import com.pkiks1.passwordmanager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.security.auth.login.CredentialException;
import javax.security.auth.login.CredentialNotFoundException;
import javax.security.auth.login.LoginException;
import java.util.Map;

@Controller
@RequestMapping("/settings")
public class SettingsController {

    private final UserService userService;

    @Autowired
    public SettingsController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getSettings(Model model) {
        PasswordManagerUser user = (PasswordManagerUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        model.addAttribute("username", user.getUsername());
        model.addAttribute("userId", user.getId());
        return "settings";
    }

    @PostMapping
    public String editUser(
            @RequestParam(value = "changePassword", required= false, defaultValue = "false") boolean changePassword,
            @RequestParam(name = "userId") String userId,
            @RequestParam(name = "username") String username,
            @RequestParam(name = "currentPassword") char[] firstPassword,
            @RequestParam(name = "newPassword") char[] newPassword,
            @RequestParam(name = "newPasswordSecond") char[] newPasswordSecond,
            @RequestParam Map<String,String> allParams,
            Model model) {
        PasswordManagerUser user = (PasswordManagerUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("passedParams",  allParams.entrySet());

        if(userId.equals(user.getId()))
        {
            if (!changePassword) {
                try {
                    boolean updateComplete = userService.updateUserWithoutPassword(user.getId(), username, firstPassword);
                    if (updateComplete) {
                        return "logout-form";
                    } else {
                        model.addAttribute("incorrectUserName", true);
                        return getSettings(model);
                    }
                } catch (CredentialException e) {
                    model.addAttribute("incorrectData", true);
                    return getSettings(model);
                }
            }
            if(username.equals(user.getUsername())){
                try {
                    if (!userService.updateUserPassword(user.getId(),username,firstPassword,newPassword,newPasswordSecond)) {
                        model.addAttribute("error", true);
                        return getSettings(model);
                    }
                } catch (CredentialException e) {
                    model.addAttribute("incorrectData", true);
                    return getSettings(model);
                }
            }else{
                try{
                    boolean updateComplete = userService.updateUserWithPassword(user.getId(),username,firstPassword,newPassword,newPasswordSecond);
                    if(updateComplete){
                        model.addAttribute("error", false);
                        return "logout-form";

                    }else{
                        model.addAttribute("error", true);
                        return getSettings(model);
                    }
                } catch (CredentialException e) {
                    model.addAttribute("incorrectData", true);
                    return getSettings(model);
                } catch (LoginException e) {
                    model.addAttribute("incorrectUserName", true);
                    return getSettings(model);
                }
            }



        }
        else
        {
            model.addAttribute("error", true);//zalogowano na inne konto lub zmieniono username w ukrytym input
        }
        model.addAttribute("successfulEditUser", true);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("userId", user.getId());
        return "settings";
    }


}


