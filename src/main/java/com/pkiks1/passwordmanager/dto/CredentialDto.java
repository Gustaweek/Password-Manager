package com.pkiks1.passwordmanager.dto;

import java.util.Objects;

public final class CredentialDto {

    private String title;
    private String email;
    private char[] password;

    private CredentialDto(String title, String email, char[] password) {
        this.title = title;
        this.email = email;
        this.password = password;
    }

    public static CredentialDto create(String title, String email, char[] password) {
        return new CredentialDto(title, email, password);
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
        return title.equals(that.title) && email.equals(that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, email);
    }

    @Override
    public String toString() {
        return "CredentialDto{" +
                "title='" + title + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
