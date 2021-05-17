package com.pkiks1.passwordmanager.dto;

import java.util.Objects;

public final class UserDto {

    private final String id;
    private final String login;
    private final char[] password;

    private UserDto(String id, String login, char[] password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public static UserDto createNewUserDto(String login, char[] password) {
        return new UserDto("", login, password);
    }

    public static UserDto createExistingUserDto(String id, String login, char[] password) {
        return new UserDto(id, login, password);
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
        return Objects.equals(id, userDto.id) && login.equals(userDto.login);
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
