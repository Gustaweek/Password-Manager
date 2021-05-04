package com.pkiks1.passwordmanager.repositories;

import com.pkiks1.passwordmanager.domain.CredentialEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CredentialRepository extends JpaRepository<CredentialEntity, String> {
}
