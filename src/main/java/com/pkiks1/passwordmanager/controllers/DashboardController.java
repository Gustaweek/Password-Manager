package com.pkiks1.passwordmanager.controllers;

import com.pkiks1.passwordmanager.domain.CredentialEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
//todo: REFACTOR
@Controller
public class DashboardController {
    //list of credentials
    @GetMapping({"/dashboard"})
    public String index(Model model, @RequestParam(required = false) String action) {
        //todo: delete credentials arraylist
        List credentials = getRandomCreds();
        model.addAttribute("credentials", credentials);

        if ("add".equals(action)) {
            model.addAttribute("addCredential", true);
            model.addAttribute("canEdit", true);//todo: get credential id and add to the model
        }

        return "dashboard";
    }

    //details of credential
    @GetMapping({"/dashboard/{credentialId}"})
    public String index(@PathVariable String credentialId, Model model) {
        //todo: delete credentials arraylist
        List credentials = getRandomCreds();
        model.addAttribute("credentials", credentials);

        //todo:delete ccredential
        model.addAttribute("credential", credentials.get(2));
        return "dashboard";
    }

    //actions on credentials
    @PostMapping({"/dashboard/{credentialId}"})
    public String index(@PathVariable String credentialId, @RequestParam(required = false) String button, Model model) {
        //todo: delete credentials arraylist
        List credentials = getRandomCreds();
        model.addAttribute("credentials", credentials);

        //random credential, todo:delete
        model.addAttribute("credential", credentials.get(2));

        if ("edit".equals(button))
            model.addAttribute("canEdit", true);//todo: get credential id and add to the model
        else if ("delete".equals(button))
            model.addAttribute("delete", true);
        else {
            //none
        }
        return "dashboard";
    }

    private List getRandomCreds() {
        List credentials = new ArrayList();
        for (int i = 0; i < 5; i++) {
            credentials.add(new CredentialEntity("Facebook.com", "user1@d.com", new char[5], null));
            credentials.add(new CredentialEntity("Twitter.com", "user2@d.com", new char[5], null));
            credentials.add(new CredentialEntity("Wikipedia.org", "user4652360@dfdsfsd.com", new char[5], null));
            credentials.add(new CredentialEntity("Gmail.com", "user1@d.com", new char[5], null));
            credentials.add(new CredentialEntity("Snapchat", "user2@d.com", new char[5], null));
            credentials.add(new CredentialEntity("Skype", "user4652360@dfdsfsd.com", new char[5], null));
            credentials.add(new CredentialEntity("Apple.com", "user1@d.com", new char[5], null));
            credentials.add(new CredentialEntity("Blogspot.com", "user2@d.com", new char[5], null));
            credentials.add(new CredentialEntity("Linkedin.com", "user4652360@dfdsfsd.com", new char[5], null));
            credentials.add(new CredentialEntity("Adobe.com", "user1@d.com", new char[5], null));
            credentials.add(new CredentialEntity("Github.com", "user2@d.com", new char[5], null));
            credentials.add(new CredentialEntity("Facebook.com", "user1@d.com", new char[5], null));
            credentials.add(new CredentialEntity("Twitter.com", "user2@d.com", new char[5], null));
            credentials.add(new CredentialEntity("Wikipedia.org", "user4652360@dfdsfsd.com", new char[5], null));
            credentials.add(new CredentialEntity("Gmail.com", "user1@d.com", new char[5], null));
            credentials.add(new CredentialEntity("Snapchat", "user2@d.com", new char[5], null));
            credentials.add(new CredentialEntity("Skype", "user4652360@dfdsfsd.com", new char[5], null));
            credentials.add(new CredentialEntity("Apple.com", "user1@d.com", new char[5], null));
            credentials.add(new CredentialEntity("Blogspot.com", "user2@d.com", new char[5], null));
            credentials.add(new CredentialEntity("Linkedin.com", "user4652360@dfdsfsd.com", new char[5], null));
            credentials.add(new CredentialEntity("Adobe.com", "user1@d.com", new char[5], null));
            credentials.add(new CredentialEntity("Github.com", "user2@d.com", new char[5], null));
        }
        return credentials;
    }


}
