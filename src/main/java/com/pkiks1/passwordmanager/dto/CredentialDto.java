package com.pkiks1.passwordmanager.dto;

import java.util.Objects;

public final class CredentialDto {

    private final String id;
    private final String userId;
    private final String title;
    private final String email;
    private final char[] password;

    private CredentialDto(String id, String userId, String title, String email, char[] password) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.email = email;
        this.password = password;
    }

    public static CredentialDto createNewCredentialDto(String userId, String title, String email, char[] password) {
        return new CredentialDto("", userId, title, email, password);
    }

    public static CredentialDto createExistingCredentialDto(String id, String userId, String title, String email, char[] password) {
        return new CredentialDto(id, userId, title, email, password);
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getEmail() {
        return email;
    }

    public char[] getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CredentialDto that = (CredentialDto) o;
        return Objects.equals(id, that.id) && userId.equals(that.userId) && title.equals(that.title) && email.equals(that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, title, email);
    }

    @Override
    public String toString() {
        return "CredentialDto{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", title='" + title + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
