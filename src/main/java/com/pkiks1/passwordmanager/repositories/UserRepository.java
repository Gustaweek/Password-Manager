package com.pkiks1.passwordmanager.repositories;

import com.pkiks1.passwordmanager.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findUserEntityByLogin(String login);
}
