package com.pkiks1.passwordmanager.repositories;

import com.pkiks1.passwordmanager.domain.CredentialEntity;
import com.pkiks1.passwordmanager.domain.UserEntity;
import com.pkiks1.passwordmanager.dto.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.LinkedList;
import java.util.List;

public interface CredentialRepository extends JpaRepository<CredentialEntity, String> {

    List<CredentialEntity> findCredentialEntityByUser(UserEntity userEntity);

}
