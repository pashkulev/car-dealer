package com.vankata.cardealer.domain.dto.part;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class SimplePartDto {

    @Size(min = 3, message = "Part name should contain at least 3 characters")
    @NotBlank(message = "Part name can not be blank")
    private String name;

    private BigDecimal price;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
