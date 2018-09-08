package com.vankata.cardealer.domain.dto.customer;

import java.math.BigDecimal;

public class CustomerSalesDto {

    private String name;

    private int boughtCarsCount;

    private BigDecimal totalSpentMoneyOnCars;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBoughtCarsCount() {
        return this.boughtCarsCount;
    }

    public void setBoughtCarsCount(int boughtCarsCount) {
        this.boughtCarsCount = boughtCarsCount;
    }

    public BigDecimal getTotalSpentMoneyOnCars() {
        return this.totalSpentMoneyOnCars;
    }

    public void setTotalSpentMoneyOnCars(BigDecimal totalSpentMoneyOnCars) {
        this.totalSpentMoneyOnCars = totalSpentMoneyOnCars;
    }
}
