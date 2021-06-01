package com.pkiks1.passwordmanager.controllers;

import com.pkiks1.passwordmanager.dto.CredentialDto;
import com.pkiks1.passwordmanager.dto.UserDto;
import com.pkiks1.passwordmanager.services.CredentialService;
import com.pkiks1.passwordmanager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

//todo: REFACTOR
@Controller
public class DashboardController {

    //TODO remove
    private static final UserDto testUser = new UserDto.UserDtoBuilder().withId("1252f84c-3d3c-4690-9c61-b9951d5af5f5").build();

    private final CredentialService credentialService;
    private final UserService userService;

    @Autowired
    public DashboardController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    //list of credentials
    @GetMapping({"/dashboard"})
    public String listAllCredentials(Model model, @RequestParam(required = false) String action) {
        //todo: delete credentials arraylist

        model.addAttribute("credentials", credentialService.getAllCredentialsForUser(testUser));

        if ("add".equals(action)) {
            model.addAttribute("addCredential", true);
            model.addAttribute("canEdit", true);//todo: get credential id and add to the model
        }

        return "dashboard";
    }

    //details of credential
    @GetMapping({"/dashboard/{credentialId}"})
    public String getSelectedCredential(@PathVariable String credentialId, Model model) {
        //todo: delete credentials arraylist
        model.addAttribute("credentials", credentialService.getAllCredentialsForUser(testUser));
        model.addAttribute("credential", credentialService.getCredentialForUser(new CredentialDto.CredentialDtoBuilder().withUserId(testUser.getId()).withId(credentialId).build()).get());
        return "dashboard";
    }

    //actions on credentials
    @PostMapping({"/dashboard/{credentialId}"})
    public String updateCredential(@PathVariable String credentialId,
                                   @RequestParam(required = false) String button,
                                   @RequestParam(name = "title") String title,
                                   @RequestParam(name = "email") String email,
                                   @RequestParam(name = "password") String password,
                                   @RequestParam(name = "note") String note,
                                   Model model) {

        credentialService.updateCredential(new CredentialDto.CredentialDtoBuilder().withId(credentialId).withTitle(title).withEmail(email).withPassword(password.toCharArray()).build());
        //todo: delete credentials arraylist
        model.addAttribute("credentials", credentialService.getAllCredentialsForUser(testUser));
        model.addAttribute("credential", credentialService.getCredentialForUser(new CredentialDto.CredentialDtoBuilder().withUserId(testUser.getId()).withId(credentialId).build()).get());

        if ("edit".equals(button))
            model.addAttribute("canEdit", true);//todo: get credential id and add to the model
        else if ("delete".equals(button))
            model.addAttribute("delete", true);
        else {
            //none
        }
        return "dashboard";
    }
}
