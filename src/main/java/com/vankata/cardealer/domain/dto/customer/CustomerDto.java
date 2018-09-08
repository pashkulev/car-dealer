package com.vankata.cardealer.domain.dto.customer;

import com.vankata.cardealer.validation.AdultVerification;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class CustomerDto {

    private long id;

    @NotBlank(message = "Name is required and can not be empty!")
    @Size(min = 4, message = "Name must be at least 4 characters")
    private String name;

    @NotNull(message = "Birth date is required and can not be empty")
    @AdultVerification
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    private boolean isYoungDriver;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return this.birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isYoungDriver() {
        return this.isYoungDriver;
    }

    public void setYoungDriver(boolean isYoungDriver) {
        this.isYoungDriver = isYoungDriver;
    }
}
