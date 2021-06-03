package com.pkiks1.passwordmanager.services;


import com.pkiks1.passwordmanager.domain.CredentialEntity;
import com.pkiks1.passwordmanager.domain.UserEntity;
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


    public void createCredential(CredentialDto credentialDto) {
        //TODO validation credentialDto

        credentialRepository.save(new CredentialEntity(credentialDto.getTitle(),
                credentialDto.getEmail(),
                credentialDto.getPassword(),
                userRepository.findById(credentialDto.getUserId()).get()));
    }

    public void updateCredential(CredentialDto credentialDto){
        Optional<CredentialEntity> optionalCredentialEntity;
        optionalCredentialEntity = credentialRepository.findById(credentialDto.getId());
        if(optionalCredentialEntity.isPresent()){
            CredentialEntity credentialEntity = optionalCredentialEntity.get();
            credentialEntity.setTitle(credentialDto.getTitle());
            credentialEntity.setEmail(credentialDto.getEmail());
            credentialEntity.setPassword(credentialDto.getPassword());
            credentialRepository.save(credentialEntity);
        }
    }

    public List<CredentialDto> getAllCredentialsForUserId(String userId) {

        List<CredentialEntity> credentials;
        List<CredentialDto> credentialDtos = new LinkedList<>();
        CredentialDto credentialDto;

        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);
        if (userEntityOptional.isPresent()) {
            credentials = credentialRepository.findCredentialEntitiesByUser(userEntityOptional.get());

            for (CredentialEntity credentialEntity : credentials) {
                credentialDto = new CredentialDto.CredentialDtoBuilder()
                        .withId(credentialEntity.getId())
                        .withTitle(credentialEntity.getTitle())
                        .withEmail(credentialEntity.getEmail())
                        .build();
                credentialDtos.add(credentialDto);
            }
        }

        return credentialDtos;
    }

    public Optional<CredentialDto> getCredentialForUser(CredentialDto credentialDto) {

        Optional<CredentialEntity> optionalCredential;
        optionalCredential = credentialRepository.findById(credentialDto.getId());

        if (optionalCredential.isPresent()) {
            credentialDto = new CredentialDto.CredentialDtoBuilder()
                    .withId(optionalCredential.get().getId())
                    .withTitle(optionalCredential.get().getTitle())
                    .withEmail(optionalCredential.get().getEmail())
                    .withPassword(optionalCredential.get().getPassword())
                    .build();
        } else {
            credentialDto = null;
        }

        //TODO refactor return
        return Optional.ofNullable(credentialDto);
    }

    public void deleteCredential(String credentialId) {

        Optional<CredentialEntity> credentialEntityOptional = credentialRepository
                .findById(credentialId);

        credentialEntityOptional.ifPresent(credentialRepository::delete);
    }
}
