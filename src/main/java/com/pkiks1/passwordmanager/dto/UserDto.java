package com.pkiks1.passwordmanager.dto;

import java.util.Objects;

public final class UserDto {

    private final String login;
    private final char[] password;

    private UserDto(String login, char[] password) {
        this.login = login;
        this.password = password;
    }

    public static UserDto create(String login, char[] password) {
        return new UserDto(login, password);
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
        return login.equals(userDto.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "login='" + login + '\'' +
                '}';
    }
}
