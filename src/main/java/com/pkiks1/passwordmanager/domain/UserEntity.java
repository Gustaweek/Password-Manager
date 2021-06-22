package com.pkiks1.passwordmanager.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "user")
@Table(name = "users")
public class UserEntity {

    @Id
    @Column(unique = true, nullable = false)
    private String id;
    private String login;
    private char[] password;
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private final List<CredentialEntity> credentials = new ArrayList<>();

    protected UserEntity() {}

    public UserEntity(String login, char[] password) {
        this.id = UUID.randomUUID().toString();
        this.login = login;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public char[] getPassword() {
        return password;
    }

    public List<CredentialEntity> getCredentials() {
        return credentials;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id='" + id + '\'' +
                ", login='" + login + '\'' +
                '}';
    }
}
