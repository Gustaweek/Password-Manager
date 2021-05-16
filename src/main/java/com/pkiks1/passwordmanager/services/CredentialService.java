package com.pkiks1.passwordmanager.services;


import com.pkiks1.passwordmanager.dto.CredentialDto;
import com.pkiks1.passwordmanager.repositories.CredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CredentialService {

    private final CredentialRepository credentialRepository;

    @Autowired
    public CredentialService(CredentialRepository credentialRepository) {
        this.credentialRepository = credentialRepository;
    }
    public void createCredential (CredentialDto credentialDto){

    }

}
