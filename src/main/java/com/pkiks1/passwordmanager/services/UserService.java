package com.pkiks1.passwordmanager.services;

import com.pkiks1.passwordmanager.domain.UserEntity;
import com.pkiks1.passwordmanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialException;
import javax.security.auth.login.CredentialNotFoundException;
import javax.security.auth.login.LoginException;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean isCredentialsCorrect(String login, char[] password) {
        Optional<UserEntity> userEntityOptional = userRepository.findUserEntityByLogin(login);
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            return String.valueOf(userEntity.getPassword()).equals(String.valueOf(password));
        }
        return false;
    }

    private void createUser(String login, char[] password) {
        userRepository.save(new UserEntity(login,
                passwordEncoder.encode(new String(password)).toCharArray()));
    }

    public boolean registerUser(String login, char[] firstPassword, char[] secondPassword) throws CredentialException {
        if (!String.valueOf(firstPassword).equals(String.valueOf(secondPassword))
        || (login.length() < 4 || login.length() > 20)
        || (firstPassword.length < 4 || firstPassword.length > 20)
        || (!login.trim().equals(login) || !String.valueOf(firstPassword).trim().equals(String.valueOf(firstPassword)))) {
            throw new CredentialException("Incorrect data");
        }

        Optional<UserEntity> userEntityOptional = userRepository.findUserEntityByLogin(login);
        if (userEntityOptional.isEmpty()) {
            createUser(login, firstPassword);
            return true;
        }
        return false;
    }

    public boolean updateUserWithoutPassword (String id, String login, char[] password) throws CredentialException{
        Optional<UserEntity> userEntityOptional;
        userEntityOptional = userRepository.findById(id);
        Optional<UserEntity> userEntityOptionalByLogin = userRepository.findUserEntityByLogin(login);
        if (passwordEncoder.matches(String.valueOf(password),String.valueOf(userEntityOptional.get().getPassword()))) {
            if(userEntityOptionalByLogin.isEmpty() && !userEntityOptional.get().getLogin().equals(login)) {
                UserEntity userEntity = userEntityOptional.get();
                userEntity.setLogin(login);
                userRepository.save(userEntity);
                return true;
            }

            return false;
        }

        throw new CredentialException();
    }

    public boolean updateUserWithPassword (String id, String login, char[] oldPassword,
                                           char[] firstPassword, char[] secondPassword) throws LoginException {

        if (!String.valueOf(firstPassword).equals(String.valueOf(secondPassword))
                || (login.length() < 4 || login.length() > 20)
                || (firstPassword.length < 4 || firstPassword.length > 20)) {
            return false;
        }
        Optional<UserEntity> userEntityOptional;
        userEntityOptional = userRepository.findById(id);
        Optional<UserEntity> userEntityOptionalByLogin = userRepository.findUserEntityByLogin(login);
        if (passwordEncoder.matches(String.valueOf(oldPassword),String.valueOf(userEntityOptional.get().getPassword()))) {
            if(userEntityOptionalByLogin.isEmpty() && !userEntityOptional.get().getLogin().equals(login)){
                UserEntity userEntity = userEntityOptional.get();
                userEntity.setLogin(login);
                userEntity.setPassword(passwordEncoder.encode(new String(firstPassword)).toCharArray());
                userRepository.save(userEntity);
                return true;
            }
            throw new LoginException();
        }

        throw new CredentialException();
    }
    public boolean updateUserPassword (String id, String login,  char[] oldPassword,
                                           char[] firstPassword, char[] secondPassword) throws CredentialException {

        if (!String.valueOf(firstPassword).equals(String.valueOf(secondPassword))
                || (firstPassword.length < 4 || firstPassword.length > 20)) {
            return false;
        }
        Optional<UserEntity> userEntityOptional;
        userEntityOptional = userRepository.findById(id);
        if (passwordEncoder.matches(String.valueOf(oldPassword),String.valueOf(userEntityOptional.get().getPassword()))){
            if(userEntityOptional.get().getLogin().equals(login)){
                UserEntity userEntity = userEntityOptional.get();
                userEntity.setPassword(passwordEncoder.encode(new String(firstPassword)).toCharArray());
                userRepository.save(userEntity);
                return true;
            }

            return false;
        }
        throw new CredentialException();
    }

}
