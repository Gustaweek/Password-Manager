package com.pkiks1.passwordmanager.services;


import com.pkiks1.passwordmanager.domain.CredentialEntity;
import com.pkiks1.passwordmanager.dto.CredentialDto;
import com.pkiks1.passwordmanager.dto.UserDto;
import com.pkiks1.passwordmanager.repositories.CredentialRepository;
import com.pkiks1.passwordmanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


@Service
public class CredentialService {

    private final CredentialRepository credentialRepository;
    private final UserRepository userRepository;

    @Autowired
    public CredentialService(CredentialRepository credentialRepository, UserRepository userRepository) {
        this.credentialRepository = credentialRepository;
        this.userRepository = userRepository;
    }

    public void createCredential (CredentialDto credentialDto){
        //TODO validation credentialDto

        credentialRepository.save(new CredentialEntity(credentialDto.getTitle(),
                credentialDto.getEmail(),
                credentialDto.getPassword(),
                userRepository.findById(credentialDto.getUserId()).get()));
    }

    public List<CredentialDto> allCredentialsForUser(UserDto userDto){

        List<CredentialEntity> allCredentialsEntity;
        List<CredentialDto> allCredentialsDto = new LinkedList<>();
        CredentialDto credentialDto;
        allCredentialsEntity = credentialRepository.findCredentialEntityByUser(userEntity.getId());
        for( CredentialEntity credentialEntity : allCredentialsEntity){
            credentialDto = new CredentialDto.CredentialDtoBuilder()
                    .withId(credentialEntity.getId())
                    .withTitle(credentialEntity.getTitle())
                    .withEmail(credentialEntity.getEmail())
                    .build();
            allCredentialsDto.add(credentialDto);
        }

        return allCredentialsDto;
    }
    public CredentialDto oneCredentialForUser(CredentialDto credentialDto){

        Optional<CredentialEntity> optionalCredentialEntity;
        optionalCredentialEntity= credentialRepository.findById(credentialDto.getId());
        if(optionalCredentialEntity.isPresent()){
            credentialDto = new CredentialDto.CredentialDtoBuilder()
                    .withId()
                    .withTitle()
                    .withEmail()
                    .withPassword()
                    .build();
        }

        return credentialDto;
    }

}
