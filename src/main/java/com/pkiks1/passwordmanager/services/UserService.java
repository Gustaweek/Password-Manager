package com.pkiks1.passwordmanager.services;

import com.pkiks1.passwordmanager.domain.UserEntity;
import com.pkiks1.passwordmanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialException;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        userRepository.save(new UserEntity(login, password));
    }

    public boolean registerUser(String login, char[] firstPassword, char[] secondPassword) throws CredentialException {
        if (!String.valueOf(firstPassword).equals(String.valueOf(secondPassword))
        || (login.length() < 5 || login.length() > 20)
        || (firstPassword.length < 5 || firstPassword.length > 20)) {
            throw new CredentialException("Incorrect data");
        }

        Optional<UserEntity> userEntityOptional = userRepository.findUserEntityByLogin(login);
        if (userEntityOptional.isEmpty()) {
            createUser(login, firstPassword);
            return true;
        }

        return false;
    }
}
