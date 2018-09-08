package com.vankata.cardealer.service;

import com.vankata.cardealer.domain.dto.customer.CustomerDto;
import com.vankata.cardealer.domain.dto.customer.CustomerSalesDto;
import com.vankata.cardealer.domain.entity.Customer;

import java.util.List;

public interface CustomerService {

    CustomerDto findById(long id);

    List<CustomerDto> findAllAscending();

    List<CustomerDto> findAllDescending();

    List<CustomerDto> findAllOrderedByName();

    CustomerSalesDto getTotalSalesByCustomerId(long id);

    Customer findByName(String name);

    boolean addCustomer(CustomerDto customerDto);

    void editCustomer(CustomerDto customerDto);
}
