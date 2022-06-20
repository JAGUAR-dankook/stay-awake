package com.cgh.server.dto;

import com.cgh.server.domain.Role;

public class RegistryRequest {

    private String username;
    private String password;
    private Role role = Role.ROLE_MEMBER;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
