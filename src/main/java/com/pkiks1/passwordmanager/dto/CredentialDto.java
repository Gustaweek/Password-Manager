package com.pkiks1.passwordmanager.dto;

import java.util.Objects;

public final class CredentialDto {

    private final String userId;
    private final String title;
    private final String email;
    private final char[] password;

    private CredentialDto(String userId, String title, String email, char[] password) {
        this.userId = userId;
        this.title = title;
        this.email = email;
        this.password = password;
    }

    public static CredentialDto create(String userId, String title, String email, char[] password) {
        return new CredentialDto(userId, title, email, password);
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

        return userId.equals(that.userId);
    }

    @Override
    public int hashCode() {
        return userId.hashCode();
    }

    @Override
    public String toString() {
        return "CredentialDto{" +
                "userId='" + userId + '\'' +
                ", title='" + title + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
