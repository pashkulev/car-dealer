package com.vankata.cardealer.domain.dto.user;

import javax.validation.constraints.Size;

public class UserLoginDto {

    private String id;

    @Size(message = "Username should contain at least 2 symbols", min = 2)
    private String username;

    @Size(message = "Password should contain at least 4 symbols", min = 4)
    private String password;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
