package com.pkiks1.passwordmanager.security;

import com.pkiks1.passwordmanager.domain.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class FakeUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return new PasswordManagerUser(new UserEntity("testUser", "testPass".toCharArray()));
    }
}
