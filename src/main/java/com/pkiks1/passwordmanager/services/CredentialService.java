package com.pkiks1.passwordmanager.services;


import com.pkiks1.passwordmanager.dto.CredentialDto;
import com.pkiks1.passwordmanager.repositories.CredentialRepository;
import com.pkiks1.passwordmanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CredentialService {

    private final CredentialRepository credentialRepository;
    // private final UserRepository userRepository;

    @Autowired
    public CredentialService(CredentialRepository credentialRepository) {
        this.credentialRepository = credentialRepository;
    }
/*
    public CredentialService(CredentialRepository credentialRepository, UserRepository userRepository) {
        this.credentialRepository = credentialRepository;
        this.userRepository = userRepository;
    }*/

    public void createCredential (CredentialDto credentialDto){


    }

}
