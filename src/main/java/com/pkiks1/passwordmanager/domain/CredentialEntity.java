package com.pkiks1.passwordmanager.domain;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "credential")
@Table(name = "credentials")
public class CredentialEntity {

    @Id
    @Column(unique = true, nullable = false)
    private String id;
    private String title;
    private String email;
    private char[] password;
    @ManyToOne
    private UserEntity user;

    protected CredentialEntity() {}

    public CredentialEntity(String title, String email, char[] password, UserEntity user) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.email = email;
        this.password = password;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public UserEntity getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CredentialEntity that = (CredentialEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "CredentialEntity{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", email='" + email + '\'' +
                ", user=" + user +
                '}';
    }
}
