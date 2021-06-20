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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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

        userService.registerUser(userName,password, password);

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
    void updateUserWithoutPassword() {
    }

    @Test
    void updateUserWithPassword() {
    }

    @Test
    void updateUserPassword() {
    }
}