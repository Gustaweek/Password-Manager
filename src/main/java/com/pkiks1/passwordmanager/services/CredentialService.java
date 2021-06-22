package com.pkiks1.passwordmanager.services;


import com.pkiks1.passwordmanager.domain.CredentialEntity;
import com.pkiks1.passwordmanager.domain.UserEntity;
import com.pkiks1.passwordmanager.dto.CredentialDto;
import com.pkiks1.passwordmanager.repositories.CredentialRepository;
import com.pkiks1.passwordmanager.repositories.UserRepository;
import com.pkiks1.passwordmanager.security.AESCipher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


@Service
public class CredentialService {

    private final CredentialRepository credentialRepository;
    private final UserRepository userRepository;
    private final AESCipher cipher;

    private final String cipherKey = "XKaPdRgUkXp2s5v8";
    private final String cipherVector = "encryptionIntVec";

    @Autowired
    public CredentialService(CredentialRepository credentialRepository, UserRepository userRepository, AESCipher cipher) {
        this.credentialRepository = credentialRepository;
        this.userRepository = userRepository;
        this.cipher = cipher;
    }

    /**
     *
     * @param credentialDto
     * @return created credential Id
     */
    public String createCredential(CredentialDto credentialDto) throws Exception{
        //TODO validation credentialDto
        byte[] encryptedPasswordBytes = cipher.encryptMessage(String.valueOf(credentialDto.getPassword()).getBytes(),
                        cipherKey.getBytes(),
                        cipherVector.getBytes());
        String encryptedPassword = new String(Base64.getEncoder().encode(encryptedPasswordBytes),"UTF-8");

        CredentialEntity credential = new CredentialEntity(credentialDto.getTitle(),
                credentialDto.getEmail(),
                encryptedPassword.toCharArray(),
                userRepository.findById(credentialDto.getUserId()).get());
        credentialRepository.save(credential);
        return credential.getId();
    }

    public void updateCredential(CredentialDto credentialDto) throws Exception {
        Optional<CredentialEntity> optionalCredentialEntity;
        optionalCredentialEntity = credentialRepository.findById(credentialDto.getId());
        if (optionalCredentialEntity.isPresent()) {
            byte[] encryptedPasswordBytes = cipher.encryptMessage(String.valueOf(credentialDto.getPassword()).getBytes(),
                            cipherKey.getBytes(),
                            cipherVector.getBytes());
            String encryptedPassword = new String(Base64.getEncoder().encode(encryptedPasswordBytes),"UTF-8");
            CredentialEntity credentialEntity = optionalCredentialEntity.get();
            credentialEntity.setTitle(credentialDto.getTitle());
            credentialEntity.setEmail(credentialDto.getEmail());
            credentialEntity.setPassword(encryptedPassword.toCharArray());
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

    public Optional<CredentialDto> getCredentialForUser(CredentialDto credentialDto) throws Exception {

        Optional<CredentialEntity> optionalCredential;
        optionalCredential = credentialRepository.findById(credentialDto.getId());

        if (optionalCredential.isPresent()) {
            byte[] encryptedPasswordBytes = String.valueOf(optionalCredential.get().getPassword()).getBytes();
            byte[] decodedEncryptedPasswordBytes = Base64.getDecoder().decode(encryptedPasswordBytes);
            byte[] decryptedPasswordBytes = cipher.decryptMessage(decodedEncryptedPasswordBytes,
                            cipherKey.getBytes(),
                            cipherVector.getBytes());
            String decryptedPassword = new String(decryptedPasswordBytes, "UTF-8");
            credentialDto = new CredentialDto.CredentialDtoBuilder()
                    .withId(optionalCredential.get().getId())
                    .withTitle(optionalCredential.get().getTitle())
                    .withEmail(optionalCredential.get().getEmail())
                    .withPassword(decryptedPassword.toCharArray())
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
