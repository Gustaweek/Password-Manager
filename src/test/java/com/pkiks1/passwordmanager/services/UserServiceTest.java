package com.pkiks1.passwordmanager.services;

import com.pkiks1.passwordmanager.domain.UserEntity;
import com.pkiks1.passwordmanager.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.security.auth.login.CredentialException;
import javax.security.auth.login.LoginException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void registerUserHappyPath() {
        // given
        String userName = "testLogin";
        char[] password = "testofpassword".toCharArray();
        Optional<UserEntity> userEntity = Optional.of(new UserEntity(userName, password));

        // when
        when(userRepository.findUserEntityByLogin(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity.get());
        when(passwordEncoder.encode(anyString())).thenReturn(String.valueOf(password));

        // then
        try {
            assertTrue(userService.registerUser(userName, password, password));
        } catch (Exception ex) {
            fail("Unexpected exception");
        }

    }

    @Test
    void registerUserLoginTooShort() {
        // given
        String userName = "tes";
        char[] password = "testofpassword".toCharArray();
        String expectedMessage = "Incorrect data";

        // when
        Exception exception = assertThrows(CredentialException.class,
                () -> userService.registerUser(userName, password, password));

        // then
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void registerUserWrongSecondPassword() {
        // given
        String userName = "testLogin";
        char[] passwordOne = "testofpassword".toCharArray();
        char[] passwordTwo = "testofpass".toCharArray();
        String expectedMessage = "Incorrect data";

        // when
        Exception exception = assertThrows(CredentialException.class,
                () -> userService.registerUser(userName,passwordOne, passwordTwo));

        // then
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void registerUserPasswordTooShort() {
        // given
        String userName = "testLogin";
        char[] passwordOne = "tes".toCharArray();
        char[] passwordTwo = "tes".toCharArray();
        String expectedMessage = "Incorrect data";

        // when
        Exception exception = assertThrows(CredentialException.class,
                () -> userService.registerUser(userName,passwordOne, passwordTwo));

        // then
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void updateUserWithoutPasswordHappyPath() {
        // given
        String userName = "testLogin";
        String finalUserName= "testLoginFinal";
        char[] password = "testofpassword".toCharArray();
        char[] passwordToCheck = "testofpassword".toCharArray();
        Optional<UserEntity> userEntity = Optional.of(new UserEntity(userName, password));
        String id= userEntity.get().getId();

        // when
        when(userRepository.findById(anyString())).thenReturn(userEntity);
        when(userRepository.findUserEntityByLogin(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity.get());

        // then
        try {
            assertTrue(userService.updateUserWithoutPassword(id,finalUserName,passwordToCheck));
        } catch (Exception e) {
            fail("Unexpected exception");
        }
        assertEquals(finalUserName, userEntity.get().getLogin());
    }

    @Test
    void updateUserWithoutPasswordLoginTooShort() {
        // given
        String userName = "testLogin";
        String finalUserName= "tes";
        char[] password = "testofpassword".toCharArray();
        char[] passwordToCheck = "testofpassword".toCharArray();
        Optional<UserEntity> userEntity = Optional.of(new UserEntity(userName, password));
        String id= userEntity.get().getId();

        // when
        when(userRepository.findById(anyString())).thenReturn(userEntity);
        when(userRepository.findUserEntityByLogin(anyString())).thenReturn(userEntity);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        // then
        try {
            assertFalse(userService.updateUserWithoutPassword(id,finalUserName,passwordToCheck));
        } catch (CredentialException e) {
            fail("Unexpected exception");
        }

    }

    @Test
    void updateUserWithoutPasswordLoginExists() {
        // given
        String userName = "testLogin";
        String finalUserName= "testLogin";
        char[] password = "testofpassword".toCharArray();
        char[] passwordToCheck = "testofpassword".toCharArray();
        Optional<UserEntity> userEntity = Optional.of(new UserEntity(userName, password));
        String id= userEntity.get().getId();

        // when
        when(userRepository.findById(anyString())).thenReturn(userEntity);
        when(userRepository.findUserEntityByLogin(anyString())).thenReturn(userEntity);

        Exception exception = assertThrows(CredentialException.class,
                () -> userService.updateUserWithoutPassword(id,finalUserName,passwordToCheck));

        // then
        assertNull( exception.getMessage());
    }

    @Test
    void updateUserWithoutPasswordWrongPasswordToCheck() {
        // given
        String userName = "testLogin";
        String finalUserName= "testLoginFinal";
        char[] passwordToCheck = "testofpa".toCharArray();
        char[] password = "testofpassword".toCharArray();
        Optional<UserEntity> userEntity = Optional.of(new UserEntity(userName, password));
        String id= userEntity.get().getId();

        // when
        when(userRepository.findById(anyString())).thenReturn(userEntity);
        when(userRepository.findUserEntityByLogin(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);
        Exception exception = assertThrows(CredentialException.class,
                () -> userService.updateUserWithoutPassword(id,finalUserName,passwordToCheck));

        // then
        assertNull( exception.getMessage());
    }

    @Test
    void updateUserWithPasswordHappyPath() {
        // given
        String userName = "testLogin";
        String finalUserName= "testLoginFinal";
        char[] password = "testofpassword".toCharArray();
        char[] passwordToCheck = "testofpassword".toCharArray();
        char[] newPasswordOne = "testofnewpassword".toCharArray();
        char[] newPasswordTwo = "testofnewpassword".toCharArray();
        Optional<UserEntity> userEntity = Optional.of(new UserEntity(userName, password));
        String id= userEntity.get().getId();

        // when
        when(userRepository.findById(anyString())).thenReturn(userEntity);
        when(userRepository.findUserEntityByLogin(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(passwordEncoder.encode(anyString())).thenReturn(String.valueOf(newPasswordOne));
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity.get());

        // then
        try {
            assertTrue(userService.updateUserWithPassword(id,finalUserName,passwordToCheck,newPasswordOne,newPasswordTwo));
        } catch (Exception e) {
            fail("Unexpected exception");
        }
        assertEquals(finalUserName, userEntity.get().getLogin());
        assertEquals(String.valueOf(newPasswordOne), String.valueOf(userEntity.get().getPassword()));
    }

    @Test
    void updateUserWithPasswordLoginTooShort() {
        // given
        String userName = "testLogin";
        String finalUserName= "tes";
        char[] password = "testofpassword".toCharArray();
        char[] passwordToCheck = "testofpassword".toCharArray();
        char[] newPasswordOne = "testofnewpassword".toCharArray();
        char[] newPasswordTwo = "testofnewpassword".toCharArray();
        Optional<UserEntity> userEntity = Optional.of(new UserEntity(userName, password));
        String id= userEntity.get().getId();

        // no when

        // then
        try {
            assertFalse(userService.updateUserWithPassword(id,finalUserName,passwordToCheck, newPasswordOne, newPasswordTwo));
        } catch (LoginException e) {
            fail("Unexpected exception");
        };
    }

    @Test
    void updateUserWithPasswordPasswordTooShort() {
        // given
        String userName = "testLogin";
        String finalUserName= "testLoginFinal";
        char[] password = "testofpassword".toCharArray();
        char[] passwordToCheck = "testofpassword".toCharArray();
        char[] newPasswordOne = "tes".toCharArray();
        char[] newPasswordTwo = "tes".toCharArray();
        Optional<UserEntity> userEntity = Optional.of(new UserEntity(userName, password));
        String id= userEntity.get().getId();

        // no when

        // then
        try {
            assertFalse(userService.updateUserWithPassword(id,finalUserName,passwordToCheck, newPasswordOne, newPasswordTwo));
        } catch (LoginException e) {
            fail("Unexpected exception");
        };
    }

    @Test
    void updateUserWithPasswordWrongNewPassword() {
        // given
        String userName = "testLogin";
        String finalUserName= "testLoginFinal";
        String expectedMessage = "Incorrect data";
        char[] password = "testofpassword".toCharArray();
        char[] passwordToCheck = "testofpassword".toCharArray();
        char[] newPasswordOne = "testofpasswordfinal".toCharArray();
        char[] newPasswordTwo = "testofpassword".toCharArray();
        Optional<UserEntity> userEntity = Optional.of(new UserEntity(userName, password));
        String id= userEntity.get().getId();

        //no when necessary

        // then
        try {
            assertFalse(userService.updateUserWithPassword(id,finalUserName,passwordToCheck, newPasswordOne, newPasswordTwo));
        } catch (LoginException e) {
            fail("Unexpected exception");
        }
    }
    @Test
    void updateUserWithPasswordLoginExists() {
        // given
        String userName = "testLogin";
        String finalUserName= "testLogin";
        char[] password = "testofpassword".toCharArray();
        char[] passwordToCheck = "testofpassword".toCharArray();
        char[] newPasswordOne = "testofpasswordfinal".toCharArray();
        char[] newPasswordTwo = "testofpasswordfinal".toCharArray();
        String expectedMessage = "null";
        Optional<UserEntity> userEntity = Optional.of(new UserEntity(userName, password));
        String id= userEntity.get().getId();

        // when
        when(userRepository.findById(anyString())).thenReturn(userEntity);
        when(userRepository.findUserEntityByLogin(anyString())).thenReturn(userEntity);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        Exception exception = assertThrows(LoginException.class,
                () -> userService.updateUserWithPassword(id,finalUserName,passwordToCheck, newPasswordOne, newPasswordTwo));

        // then
        assertNull( exception.getMessage());
    }

    @Test
    void updateUserWithPasswordWrongPasswordToCheck() {
        // given
        String userName = "testLogin";
        String finalUserName= "testLoginFinal";
        char[] password = "testofpassword".toCharArray();
        char[] passwordToCheck = "testofpass".toCharArray();
        char[] newPasswordOne = "testofpasswordfinal".toCharArray();
        char[] newPasswordTwo = "testofpasswordfinal".toCharArray();
        Optional<UserEntity> userEntity = Optional.of(new UserEntity(userName, password));
        String id= userEntity.get().getId();

        // when
        when(userRepository.findById(anyString())).thenReturn(userEntity);
        when(userRepository.findUserEntityByLogin(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        // then
        Exception exception = assertThrows(LoginException.class,
                () -> userService.updateUserWithPassword(id,finalUserName,passwordToCheck, newPasswordOne, newPasswordTwo));

        assertNull( exception.getMessage());
    }

    @Test
    void updateUserPasswordHappyPath() {
        // given
        String userName = "testLogin";
        String finalUserName= "testLogin";
        char[] password = "testofpassword".toCharArray();
        char[] passwordToCheck = "testofpassword".toCharArray();
        char[] newPasswordOne = "testofnewpassword".toCharArray();
        char[] newPasswordTwo = "testofnewpassword".toCharArray();
        Optional<UserEntity> userEntity = Optional.of(new UserEntity(userName, password));
        String id= userEntity.get().getId();

        // when
        when(userRepository.findById(anyString())).thenReturn(userEntity);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(passwordEncoder.encode(anyString())).thenReturn(String.valueOf(newPasswordOne));
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity.get());

        // then
        try {
            assertTrue(userService.updateUserPassword(id,finalUserName,passwordToCheck,newPasswordOne,newPasswordTwo));
        } catch (Exception e) {
            fail("Unexpected exception");
        }
        assertEquals(finalUserName, userEntity.get().getLogin());
        assertEquals(String.valueOf(newPasswordOne), String.valueOf(userEntity.get().getPassword()));
    }

    @Test
    void updateUserPasswordPasswordTooShort() {
        // given
        String userName = "testLogin";
        String finalUserName= "testLogin";
        String expectedMessage = "Incorrect data";
        char[] password = "testofpassword".toCharArray();
        char[] passwordToCheck = "testofpassword".toCharArray();
        char[] newPasswordOne = "tes".toCharArray();
        char[] newPasswordTwo = "tes".toCharArray();
        Optional<UserEntity> userEntity = Optional.of(new UserEntity(userName, password));
        String id= userEntity.get().getId();

        // no when


        // then
        try {
            assertFalse(userService.updateUserPassword(id,finalUserName,passwordToCheck, newPasswordOne, newPasswordTwo));
        } catch (CredentialException e) {
            fail("Unexpected exception");
        }
    }

    @Test
    void updateUserPasswordWrongNewPassword() {
        // given
        String userName = "testLogin";
        String finalUserName= "testLogin";
        String expectedMessage = "Incorrect data";
        char[] password = "testofpassword".toCharArray();
        char[] passwordToCheck = "testofpassword".toCharArray();
        char[] newPasswordOne = "testofpasswordfinal".toCharArray();
        char[] newPasswordTwo = "testofpassword".toCharArray();
        Optional<UserEntity> userEntity = Optional.of(new UserEntity(userName, password));
        String id= userEntity.get().getId();

        // no when


        // then
        try {
            assertFalse(userService.updateUserPassword(id,finalUserName,passwordToCheck, newPasswordOne, newPasswordTwo));
        } catch (CredentialException e) {
            fail("Unexpected exception");
        }
    }

    @Test
    void updateUserPasswordWrongPasswordToCheck() {
        // given
        String userName = "testLogin";
        String finalUserName= "testLogin";
        char[] password = "testofpassword".toCharArray();
        char[] passwordToCheck = "testofpass".toCharArray();
        char[] newPasswordOne = "testofpasswordfinal".toCharArray();
        char[] newPasswordTwo = "testofpasswordfinal".toCharArray();
        Optional<UserEntity> userEntity = Optional.of(new UserEntity(userName, password));
        String id= userEntity.get().getId();

        // when
        when(userRepository.findById(anyString())).thenReturn(userEntity);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        Exception exception = assertThrows(CredentialException.class,
                () -> userService.updateUserPassword(id,finalUserName,passwordToCheck, newPasswordOne, newPasswordTwo));

        // then
        assertNull(exception.getMessage());
    }

}