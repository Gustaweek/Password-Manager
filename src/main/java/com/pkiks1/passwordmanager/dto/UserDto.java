package com.pkiks1.passwordmanager.dto;

import java.util.Objects;

public final class UserDto {

    private final String id;
    private final String login;
    private final char[] password;

    private UserDto(UserDtoBuilder userDtoBuilder) {
        this.id = userDtoBuilder.id;
        this.login = userDtoBuilder.login;
        this.password = userDtoBuilder.password;
    }

    public static class UserDtoBuilder {

        private String id;
        private String login;
        private char[] password;

        public UserDtoBuilder() {}

        public UserDtoBuilder withId(String id) {
            this.id = id;
            return this;
        }

        public UserDtoBuilder withLogin(String login) {
            this.login = login;
            return this;
        }

        public UserDtoBuilder withPassword(char[] password) {
            this.password = password;
            return this;
        }

        public UserDto build() {
            return new UserDto(this);
        }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(id, userDto.id) && Objects.equals(login, userDto.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id='" + id + '\'' +
                ", login='" + login + '\'' +
                '}';
    }
}
