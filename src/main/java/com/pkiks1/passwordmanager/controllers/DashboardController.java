package com.pkiks1.passwordmanager.controllers;

import com.pkiks1.passwordmanager.dto.UserDto;
import com.pkiks1.passwordmanager.services.CredentialService;
import com.pkiks1.passwordmanager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
    public String index(Model model, @RequestParam(required = false) String action) {
        //todo: delete credentials arraylist

        model.addAttribute("credentials", credentialService.allCredentialsForUser(testUser));

        if ("add".equals(action)) {
            model.addAttribute("addCredential", true);
            model.addAttribute("canEdit", true);//todo: get credential id and add to the model
        }

        return "dashboard";
    }

//    //details of credential
//    @GetMapping({"/dashboard/{credentialId}"})
//    public String index(@PathVariable String credentialId, Model model) {
//        //todo: delete credentials arraylist
//        model.addAttribute("credentials", credentialService.allCredentialsForUser(testUser));
//
//        //todo:delete ccredential
//        model.addAttribute("credential", credentialService.oneCredentialForUser(new CredentialDto.CredentialDtoBuilder().withUserId(testUser.getId()).withId(credentialId).build()));
//        return "dashboard";
//    }
//
//    //actions on credentials
//    @PostMapping({"/dashboard/{credentialId}"})
//    public String index(@PathVariable String credentialId, @RequestParam(required = false) String button, Model model) {
//        //todo: delete credentials arraylist
//        model.addAttribute("credentials", credentialService.allCredentialsForUser(testUser));
//
//        //random credential, todo:delete
//        model.addAttribute("credential", credentialService.oneCredentialForUser(new CredentialDto.CredentialDtoBuilder().withUserId(testUser.getId()).withId(credentialId).build()));
//
//        if ("edit".equals(button))
//            model.addAttribute("canEdit", true);//todo: get credential id and add to the model
//        else if ("delete".equals(button))
//            model.addAttribute("delete", true);
//        else {
//            //none
//        }
//        return "dashboard";
//    }
}
