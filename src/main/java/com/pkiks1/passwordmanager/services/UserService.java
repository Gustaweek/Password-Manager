package com.pkiks1.passwordmanager.services;

import com.pkiks1.passwordmanager.domain.UserEntity;
import com.pkiks1.passwordmanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public void createUser(String login, char[] password) {
        userRepository.save(new UserEntity(login, password));
    }

    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }
}
