package com.pkiks1.passwordmanager.repositories;

import com.pkiks1.passwordmanager.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {
}
