package com.vankata.cardealer.domain.dto.user;

import javax.validation.constraints.NotBlank;

public class UserCreateDto extends UserDto {

    @NotBlank(message = "Confirm Password is required")
    private String confirmPassword;

    public String getConfirmPassword() {
        return this.confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
