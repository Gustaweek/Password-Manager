package com.pkiks1.passwordmanager.dto;

import java.util.Objects;

public final class CredentialDto {

    private final String id;
    private final String userId;
    private final String title;
    private final String email;
    private final char[] password;

    private CredentialDto(CredentialDtoBuilder credentialDtoBuilder) {
        this.id = credentialDtoBuilder.id;
        this.userId = credentialDtoBuilder.userId;
        this.title = credentialDtoBuilder.title;
        this.email = credentialDtoBuilder.email;
        this.password = credentialDtoBuilder.password;
    }

    public static class CredentialDtoBuilder {
        private String id;
        private String userId;
        private String title;
        private String email;
        private char[] password;

        public CredentialDtoBuilder() {}

        public CredentialDtoBuilder withId(String id) {
            this.id = id;
            return this;
        }

        public CredentialDtoBuilder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public CredentialDtoBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public CredentialDtoBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public CredentialDtoBuilder withPassword(char[] password) {
            this.password = password;
            return this;
        }

        public CredentialDto build() {
            return new CredentialDto(this);
        }
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

    public String getPasswordString() {
        return String.valueOf(this.getPassword());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CredentialDto that = (CredentialDto) o;
        return Objects.equals(id, that.id) && Objects.equals(userId, that.userId) && Objects.equals(title, that.title) && Objects.equals(email, that.email);
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
