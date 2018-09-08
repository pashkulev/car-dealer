package com.vankata.cardealer.domain.dto.user;

import com.vankata.cardealer.validation.EmailVerification;
import com.vankata.cardealer.validation.PasswordsVerification;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@PasswordsVerification.Passwords({
        @PasswordsVerification(password = "password", confirmPassword = "confirmPassword")
})
public class UserDto {

    private String id;

    @NotBlank(message = "Username is required")
    @Size(message = "Username should contain at least 2 characters", min = 2)
    private String username;

    @NotBlank(message = "Email is required")
    @EmailVerification
    private String email;

    @Size(message = "Password should contain at least 4 characters", min = 4)
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

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
