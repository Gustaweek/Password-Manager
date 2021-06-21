package com.pkiks1.passwordmanager.services;

import com.pkiks1.passwordmanager.domain.CredentialEntity;
import com.pkiks1.passwordmanager.domain.UserEntity;
import com.pkiks1.passwordmanager.dto.CredentialDto;
import com.pkiks1.passwordmanager.repositories.CredentialRepository;
import com.pkiks1.passwordmanager.repositories.UserRepository;
import com.pkiks1.passwordmanager.security.AESCipher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CredentialServiceTest {

    @InjectMocks
    private CredentialService credentialService;

    @Mock
    private CredentialRepository credentialRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AESCipher cipher;

    private final String cipherKey = "XKaPdRgUkXp2s5v8";
    private final String cipherVector = "encryptionIntVec";

    @Test
    void createCredentialHappyPath() {

        // given
        String userName = "testLogin";
        char[] userPassword = "testofpassword".toCharArray();
        Optional<UserEntity> userEntity = Optional.of(new UserEntity(userName, userPassword));
        String userId = userEntity.get().getId();
        String title ="testTitle";
        String email = "test@test.pl";
        char[] credentialPassword = "testofpassword".toCharArray();


        CredentialDto credentialDto = new CredentialDto.CredentialDtoBuilder()
                .withUserId(userId)
                .withTitle(title)
                .withEmail(email)
                .withPassword(credentialPassword)
                .build();
        String encrypted = "testEncrypted";

        CredentialEntity credentialEntity =new CredentialEntity(credentialDto.getTitle(),
                credentialDto.getEmail(),
                credentialPassword,
                userEntity.get());

        // when
        try {
            when(cipher.encryptMessage(String.valueOf(credentialPassword).getBytes(),cipherKey.getBytes(),cipherVector.getBytes())).thenReturn(encrypted.getBytes());
        } catch (Exception e) {
            fail("Unexpected exception");
        }
        when(userRepository.findById(anyString())).thenReturn(userEntity);
        when(credentialRepository.save(any(CredentialEntity.class))).thenReturn(credentialEntity);

        // then
        try {
            assertNotNull(credentialService.createCredential(credentialDto));
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(title, credentialEntity.getTitle());
        assertEquals(userEntity.get(), credentialEntity.getUser());

    }

    @Test
    void updateCredentialHappyPath() {
    }

    @Test
    void getAllCredentialsForUserIdHappyPath() {
    }

    @Test
    void getCredentialForUserHappyPath() {
    }

    @Test
    void deleteCredentialHappyPath() {
    }
}