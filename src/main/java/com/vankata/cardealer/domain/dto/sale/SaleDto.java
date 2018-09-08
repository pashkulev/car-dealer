package com.vankata.cardealer.domain.dto.sale;

import com.vankata.cardealer.domain.dto.car.CarDto;
import com.vankata.cardealer.domain.dto.customer.CustomerDto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SaleDto {

    private CarDto car;

    private CustomerDto customer;

    private BigDecimal price;

    private BigDecimal discountedPrice;

    private String discount;

    private LocalDate dateRegistered;

    public CarDto getCar() {
        return this.car;
    }

    public void setCar(CarDto car) {
        this.car = car;
    }

    public CustomerDto getCustomer() {
        return this.customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDiscountedPrice() {
        return this.discountedPrice;
    }

    public void setDiscountedPrice(BigDecimal discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public String getDiscount() {
        return this.discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public LocalDate getDateRegistered() {
        return this.dateRegistered;
    }

    public void setDateRegistered(LocalDate dateRegistered) {
        this.dateRegistered = dateRegistered;
    }
}
