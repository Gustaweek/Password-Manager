package com.pkiks1.passwordmanager.controllers;

import com.pkiks1.passwordmanager.dto.CredentialDto;
import com.pkiks1.passwordmanager.security.PasswordManagerUser;
import com.pkiks1.passwordmanager.services.CredentialService;
import com.pkiks1.passwordmanager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

//todo: REFACTOR
@Controller
public class DashboardController {

    private final CredentialService credentialService;
    private final UserService userService;

    @Autowired
    public DashboardController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    //list of credentials
    @GetMapping({"/dashboard"})
    public String listAllCredentials(Model model) {

        //todo how to use user of app
        PasswordManagerUser user = (PasswordManagerUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        int credentialSize = credentialService.getAllCredentialsForUserId(user.getId()).size();

        model.addAttribute("credentials", credentialService.getAllCredentialsForUserId(user.getId()));
        model.addAttribute("credentialsSize", credentialSize);

        model.addAttribute("user", user);

        return "dashboard";
    }

    //details of credential
    @GetMapping({"/dashboard/{credentialId}"})
    public String getSelectedCredential(@PathVariable String credentialId, Model model) {
        model.addAttribute("viewCredential", true);

        try {
            CredentialDto credential = credentialService
                    .getCredentialForUser(new CredentialDto.CredentialDtoBuilder().withId(credentialId).build())
                    .get();
            model.addAttribute("credential", credential);
            return listAllCredentials(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
        CredentialDto credential = new CredentialDto.CredentialDtoBuilder().withId("CREDENTIAL-GET-ERROR").build();
        model.addAttribute("credential", credential);
        return listAllCredentials(model);
    }

    @GetMapping("/dashboard/{credentialId}/edit")
    public String getCredentialEditForm(@PathVariable String credentialId, Model model) {
        model.addAttribute("viewCredential", true);
        model.addAttribute("canEdit", true);

        return getSelectedCredential(credentialId, model);
    }

    @GetMapping("/dashboard/{credentialId}/delete")
    public String deleteCredential(@PathVariable String credentialId, Model model) {

        credentialService.deleteCredential(credentialId);
        model.addAttribute("delete", true);
        return listAllCredentials(model);
    }

    @GetMapping("/dashboard/add")
    public String getCredentialAddForm(Model model) {

        model.addAttribute("addCredential", true);
        model.addAttribute("canEdit", true);

        return listAllCredentials(model);
    }

    //actions on credentials
    @PostMapping({"/dashboard/{credentialId}"})
    public String updateCredential(@PathVariable String credentialId,
                                   @RequestParam(required = false) String button,
                                   @RequestParam(name = "title") String title,
                                   @RequestParam(name = "email") String email,
                                   @RequestParam(name = "password") String password,
                                   Model model) {

        try {
            credentialService.updateCredential(new CredentialDto.CredentialDtoBuilder()
                    .withId(credentialId)
                    .withTitle(title)
                    .withEmail(email)
                    .withPassword(password.toCharArray())
                    .build());

            model.addAttribute("credential", credentialService
                    .getCredentialForUser(new CredentialDto.CredentialDtoBuilder().withId(credentialId).build())
                    .get());

            return listAllCredentials(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
        CredentialDto credential = new CredentialDto.CredentialDtoBuilder().withId("CREDENTIAL-UPDATE-ERROR").build();
        model.addAttribute("credential", credential);
        return listAllCredentials(model);
    }

    @PostMapping("/dashboard")
    public String addCredential(@RequestParam String title,
                                @RequestParam String email,
                                @RequestParam String password,
                                Model model) {

        PasswordManagerUser user = (PasswordManagerUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            String createdId=credentialService.createCredential(new CredentialDto.CredentialDtoBuilder()
                    .withUserId(user.getId())
                    .withTitle(title)
                    .withEmail(email)
                    .withPassword(password.toCharArray())
                    .build());
            model.addAttribute("createdCredentialId", createdId);
            return listAllCredentials(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String createdId = "CREDENTIAL-CREATION-ERROR";
        model.addAttribute("createdCredentialId", createdId);
        return listAllCredentials(model);
    }
}