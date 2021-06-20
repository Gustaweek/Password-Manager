package com.pkiks1.passwordmanager.services;

import com.pkiks1.passwordmanager.domain.UserEntity;
import com.pkiks1.passwordmanager.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.security.auth.login.CredentialException;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


class UserServiceTest {

    @Autowired
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    void registerUserHappyPath() throws CredentialException {

        String userName = "testLogin";
        char[] password = "testofpassword".toCharArray();

        Optional<UserEntity> userEntity = Optional.of(new UserEntity(userName, password));

        when(userRepository.findUserEntityByLogin(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity.get());
        when(passwordEncoder.encode(anyString())).thenReturn(String.valueOf(password));


        assertTrue(userService.registerUser(userName, password, password));

    }

    @Test
    void registerUserTooShortLogin() {
        String userName = "tes";
        char[] password = "testofpassword".toCharArray();

        Exception exception = Assertions.assertThrows(CredentialException.class, () -> {
            userService.registerUser(userName,password, password);
        });
        String expectedMessage = "Incorrect data";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void registerUserWrongSecondPassword() {
        String userName = "testLogin";
        char[] passwordOne = "testofpassword".toCharArray();
        char[] passwordTwo = "testofpass".toCharArray();
        Exception exception = Assertions.assertThrows(CredentialException.class, () -> {
            userService.registerUser(userName,passwordOne, passwordTwo);
        });
        String expectedMessage = "Incorrect data";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void registerUserTooShortPassword() {
        String userName = "testLogin";
        char[] passwordOne = "tes".toCharArray();
        char[] passwordTwo = "tes".toCharArray();
        Exception exception = Assertions.assertThrows(CredentialException.class, () -> {
            userService.registerUser(userName,passwordOne, passwordTwo);
        });
        String expectedMessage = "Incorrect data";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void updateUserWithoutPasswordHappyPath() throws CredentialException {

        String userName = "testLogin";
        char[] password = "testofpassword".toCharArray();

        Optional<UserEntity> userEntity = Optional.of(new UserEntity(userName, password));

        String finalUserName= "testLoginFinal";
        String id= userEntity.get().getId();
        char[] passwordToCheck = "testofpassword".toCharArray();

        when(userRepository.findById(anyString())).thenReturn(userEntity);
        when(userRepository.findUserEntityByLogin(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity.get());



        assertTrue(userService.updateUserWithoutPassword(id,finalUserName,passwordToCheck));
        assertEquals(finalUserName, userEntity.get().getLogin());
    }

    @Test
    void updateUserWithoutPasswordTooShortLogin() {
        String userName = "testLogin";
        char[] password = "testofpassword".toCharArray();

        Optional<UserEntity> userEntity = Optional.of(new UserEntity(userName, password));

        String finalUserName= "tes";
        String id= userEntity.get().getId();
        char[] passwordToCheck = "testofpassword".toCharArray();

        Exception exception = Assertions.assertThrows(CredentialException.class, () -> {
            userService.updateUserWithoutPassword(id,finalUserName,passwordToCheck);
        });
        String expectedMessage = "Incorrect Login";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void updateUserWithoutPasswordLoginExists() throws CredentialException {

        String userName = "testLogin";
        char[] password = "testofpassword".toCharArray();

        Optional<UserEntity> userEntity = Optional.of(new UserEntity(userName, password));

        String finalUserName= "testLogin";
        String id= userEntity.get().getId();
        char[] passwordToCheck = "testofpassword".toCharArray();

        when(userRepository.findById(anyString())).thenReturn(userEntity);
        when(userRepository.findUserEntityByLogin(anyString())).thenReturn(Optional.empty());

        assertFalse(userService.updateUserWithoutPassword(id,finalUserName,passwordToCheck));
    }

    @Test
    void updateUserWithoutPasswordWrongPasswordToCheck() throws CredentialException {

        String userName = "testLogin";
        char[] password = "testofpassword".toCharArray();

        Optional<UserEntity> userEntity = Optional.of(new UserEntity(userName, password));

        String finalUserName= "testLoginFinal";
        String id= userEntity.get().getId();
        char[] passwordToCheck = "testofpa".toCharArray();

        when(userRepository.findById(anyString())).thenReturn(userEntity);
        when(userRepository.findUserEntityByLogin(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        assertFalse(userService.updateUserWithoutPassword(id,finalUserName,passwordToCheck));
    }

    @Test
    void updateUserWithPassword() {
    }

    @Test
    void updateUserPassword() {
    }
}