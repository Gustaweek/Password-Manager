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

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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
            fail("Unexpected exception");
        }
        assertEquals(title, credentialEntity.getTitle());
        assertEquals(userEntity.get(), credentialEntity.getUser());

    }

    @Test
    void updateCredentialHappyPath() {
        // given
        String userName = "testLogin";
        char[] userPassword = "testofpassword".toCharArray();
        Optional<UserEntity> userEntity = Optional.of(new UserEntity(userName, userPassword));
        String title ="testTitle";
        String email = "test@test.pl";
        char[] credentialPassword = "testofpassword".toCharArray();

        CredentialEntity credentialEntity = new CredentialEntity(title,email,credentialPassword,userEntity.get());

        String userId = userEntity.get().getId();
        String newTitle ="newTestTitle";
        String newEmail = "newTest@test.pl";
        char[] newCredentialPassword = "NewTestOfPassword".toCharArray();
        String encrypted = "testEncrypted";

        CredentialDto credentialDto = new CredentialDto.CredentialDtoBuilder()
                .withId(credentialEntity.getId())
                .withUserId(userId)
                .withTitle(title)
                .withEmail(email)
                .withPassword(credentialPassword)
                .build();
        CredentialDto updatedCredentialDto = new CredentialDto.CredentialDtoBuilder()
                .withId(credentialEntity.getId())
                .withUserId(userId)
                .withTitle(newTitle)
                .withEmail(newEmail)
                .withPassword(newCredentialPassword)
                .build();
        Optional<CredentialEntity> credentialEntityOptional = Optional.of(credentialEntity);

        // when
        when(credentialRepository.findById(anyString())).thenReturn(credentialEntityOptional);
        try {
            when(cipher.encryptMessage(String.valueOf(newCredentialPassword).getBytes(),cipherKey.getBytes(),cipherVector.getBytes())).thenReturn(encrypted.getBytes());
        } catch (Exception e) {
            fail("Unexpected exception");
        }
        when(credentialRepository.save(any(CredentialEntity.class))).thenReturn(credentialEntityOptional.get());

        // then
        try {
            credentialService.updateCredential(updatedCredentialDto);
        } catch (Exception e) {
            fail("Unexpected exception");
        }
        assertEquals(updatedCredentialDto.getTitle(),credentialEntityOptional.get().getTitle());
        assertEquals(updatedCredentialDto.getEmail(),credentialEntityOptional.get().getEmail());

    }

    @Test
    void getAllCredentialsForUserIdHappyPath() {
        // given
        String userName = "testLogin";
        char[] userPassword = "testofpassword".toCharArray();
        Optional<UserEntity> userEntity = Optional.of(new UserEntity(userName, userPassword));
        String userId = userEntity.get().getId();
        String title ="testTitle";
        String email = "test@test.pl";
        char[] credentialPassword = "testofpassword".toCharArray();
        CredentialEntity credentialEntity =new CredentialEntity(title,
                email,
                credentialPassword,
                userEntity.get());
        List<CredentialEntity> credentials = new LinkedList<>();

        credentials.add(credentialEntity);

        //when
        when(userRepository.findById(anyString())).thenReturn(userEntity);
        when(credentialRepository.findCredentialEntitiesByUser(any(UserEntity.class))).thenReturn(credentials);

        //then
        List<CredentialDto> credentialDtos ;
        credentialDtos = credentialService.getAllCredentialsForUserId(userId);
        assertEquals(1,credentialDtos.size());
    }

    @Test
    void getCredentialForUserHappyPath() {
        // given
        String userName = "testLogin";
        char[] userPassword = "testofpassword".toCharArray();
        Optional<UserEntity> userEntity = Optional.of(new UserEntity(userName, userPassword));
        String userId = userEntity.get().getId();
        String title ="testTitle";
        String email = "test@test.pl";
        char[] credentialPasswordEncode = "dGVzdG9mcGFzc3dvcmQ=".toCharArray();
        char[] credentialPassword = "testofpassword".toCharArray();
        Optional<CredentialEntity> credentialEntityOptional = Optional.of(new CredentialEntity(title, email, credentialPasswordEncode, userEntity.get()));

        CredentialDto credentialDto = new CredentialDto.CredentialDtoBuilder()
                .withId(credentialEntityOptional.get().getId())
                .withUserId(userId)
                .withTitle(title)
                .withEmail(email)
                .withPassword(credentialPasswordEncode)
                .build();
        Optional<CredentialDto> credentialDtoOptional = Optional.of(credentialDto);

        //when
        when(credentialRepository.findById(anyString())).thenReturn(credentialEntityOptional);
        try {
            when(cipher.decryptMessage(String.valueOf(credentialPassword).getBytes(),cipherKey.getBytes(),cipherVector.getBytes())).thenReturn(String.valueOf(credentialPassword).getBytes());
        } catch (Exception e) {
            fail("Unexpected exception");
        }
        //then
        try {
            credentialDtoOptional = credentialService.getCredentialForUser(credentialDto);
        } catch (Exception e) {
            fail("Unexpected exception");
        }
        assertNotNull(credentialDtoOptional);
        assertEquals(credentialDtoOptional.get().getEmail(),credentialDto.getEmail());
        assertEquals(credentialDtoOptional.get().getTitle(),credentialDto.getTitle());
    }

    @Test
    void deleteCredentialHappyPath() {
        // given
        String userName = "testLogin";
        char[] userPassword = "testofpassword".toCharArray();
        Optional<UserEntity> userEntity = Optional.of(new UserEntity(userName, userPassword));
        String title ="testTitle";
        String email = "test@test.pl";
        char[] credentialPasswordEncode = "dGVzdG9mcGFzc3dvcmQ=".toCharArray();
        Optional<CredentialEntity> credentialEntityOptional = Optional.of(new CredentialEntity(title, email, credentialPasswordEncode, userEntity.get()));
        List<CredentialEntity> credentialEntities =new ArrayList<>();
        credentialEntities.add(credentialEntityOptional.get());
        //when
        when(credentialRepository.findById(anyString())).thenReturn(credentialEntityOptional);
        doNothing().when(credentialRepository).delete(any(CredentialEntity.class));
        credentialEntities.remove(credentialEntityOptional.get());
        //then
        credentialService.deleteCredential(credentialEntityOptional.get().getId());
        verify(credentialRepository, times(1)).delete(any(CredentialEntity.class));

    }
}