package com.vankata.cardealer.service;

import com.vankata.cardealer.domain.dto.customer.CustomerDto;
import com.vankata.cardealer.domain.dto.customer.CustomerSalesDto;
import com.vankata.cardealer.domain.entity.Customer;
import com.vankata.cardealer.domain.entity.Part;
import com.vankata.cardealer.domain.mapper.ModelParser;
import com.vankata.cardealer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final ModelParser modelParser;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, ModelParser modelParser) {
        this.customerRepository = customerRepository;
        this.modelParser = modelParser;
    }

    @Override
    public CustomerDto findById(long id) {
        Customer foundCustomer = this.customerRepository.findById(id);
        if (foundCustomer != null) {
            return this.modelParser.convert(foundCustomer, CustomerDto.class);
        }

        return null;
    }

    @Override
    public Customer findByName(String name) {
        return this.customerRepository.findByName(name);
    }

    @Override
    public List<CustomerDto> findAllAscending() {
        List<Customer> customers = this.customerRepository.findAllByBirthDateAndYoungDriver();
        CustomerDto[] customerDtos = this.modelParser.convert(customers, CustomerDto[].class);
        return Arrays.asList(customerDtos);
    }

    @Override
    public List<CustomerDto> findAllDescending() {
        List<Customer> customers = this.customerRepository.findAllByOrderByBirthDateDescIsYoungDriver();
        CustomerDto[] customerDtos = this.modelParser.convert(customers, CustomerDto[].class);
        return Arrays.asList(customerDtos);
    }

    @Override
    public List<CustomerDto> findAllOrderedByName() {
        List<Customer> customers = this.customerRepository.findAllByOrderByName();
        CustomerDto[] customerDtos = this.modelParser.convert(customers, CustomerDto[].class);
        return Arrays.asList(customerDtos);
    }

    @Override
    public CustomerSalesDto getTotalSalesByCustomerId(long id) {
        Customer customer = this.customerRepository.findById(id);

        CustomerSalesDto customerSalesDto = new CustomerSalesDto();
        customerSalesDto.setName(customer.getName());
        customerSalesDto.setBoughtCarsCount(customer.getSales().size());

        BigDecimal salesSum = customer.getSales().stream().map(sale -> {
            BigDecimal carPartsSum = sale.getCar().getParts().stream()
                    .map(Part::getPrice)
                    .reduce(BigDecimal::add)
                    .orElse(BigDecimal.ZERO);
            BigDecimal totalSaleSum = sale.getCar().getPrice().add(carPartsSum);
            return totalSaleSum.multiply(new BigDecimal(1 - (sale.getDiscount()
                            + (customer.isYoungDriver() && sale.getDiscount() <= 0.95 ? 0.05 : 0))));
        }).reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);

        customerSalesDto.setTotalSpentMoneyOnCars(salesSum);

        return customerSalesDto;
    }

    @Override
    public boolean addCustomer(CustomerDto customerDto) {
        this.setIsYoungDriver(customerDto);
        Customer customer = this.modelParser.convert(customerDto, Customer.class);
        return this.customerRepository.save(customer).getId() != 0;
    }

    @Override
    public void editCustomer(CustomerDto customerDto) {
        this.setIsYoungDriver(customerDto);
        Customer customer = this.modelParser.convert(customerDto, Customer.class);
        this.customerRepository.save(customer);
    }

    private void setIsYoungDriver(CustomerDto customerDto) {
        customerDto.setYoungDriver(customerDto.getBirthDate().plusYears(25).isAfter(LocalDate.now()));
    }
}
