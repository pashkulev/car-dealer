package com.vankata.cardealer.domain.dto.sale;

import com.vankata.cardealer.validation.DiscountVerification;

import javax.validation.constraints.Min;

public class SaleCreateDto {

    @Min(value = 1, message = "Customer is required!")
    private long customerId;

    @Min(value = 1, message = "Car is required!")
    private long carId;

    @DiscountVerification
    private int discount;

    public long getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public long getCarId() {
        return this.carId;
    }

    public void setCarId(long carId) {
        this.carId = carId;
    }

    public int getDiscount() {
        return this.discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}
