package com.epam.esm.dto;

import java.util.Objects;

public class AuthorizedUserDto {
    private String email;
    private String token;
    private boolean isAdmin;

    public AuthorizedUserDto(String email, String token, boolean isAdmin) {
        this.email = email;
        this.token = token;
        this.isAdmin = isAdmin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorizedUserDto that = (AuthorizedUserDto) o;
        return isAdmin == that.isAdmin && Objects.equals(email, that.email) && Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, token, isAdmin);
    }

    @Override
    public String toString() {
        return "AuthorizedUserDto{" +
                "email='" + email + '\'' +
                ", token='" + token + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
