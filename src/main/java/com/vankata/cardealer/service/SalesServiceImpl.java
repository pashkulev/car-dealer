package com.vankata.cardealer.service;

import com.vankata.cardealer.domain.dto.car.CarDto;
import com.vankata.cardealer.domain.dto.customer.CustomerDto;
import com.vankata.cardealer.domain.dto.sale.SaleCreateDto;
import com.vankata.cardealer.domain.dto.sale.SaleDetailDto;
import com.vankata.cardealer.domain.dto.sale.SaleDto;
import com.vankata.cardealer.domain.dto.sale.SaleDtoWithPercent;
import com.vankata.cardealer.domain.entity.Car;
import com.vankata.cardealer.domain.entity.Customer;
import com.vankata.cardealer.domain.entity.Part;
import com.vankata.cardealer.domain.entity.Sale;
import com.vankata.cardealer.domain.mapper.ModelParser;
import com.vankata.cardealer.repository.CarRepository;
import com.vankata.cardealer.repository.CustomerRepository;
import com.vankata.cardealer.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SalesServiceImpl implements SalesService {

    private final SaleRepository saleRepository;

    private final CustomerRepository customerRepository;

    private final CarRepository carRepository;

    private final ModelParser modelParser;

    @Autowired
    public SalesServiceImpl(SaleRepository saleRepository,
                            CustomerRepository customerRepository,
                            CarRepository carRepository,
                            ModelParser modelParser) {
        this.saleRepository = saleRepository;
        this.customerRepository = customerRepository;
        this.carRepository = carRepository;
        this.modelParser = modelParser;
    }

    @Override
    public boolean addSale(HttpSession session) {
        SaleCreateDto saleCreateDto = (SaleCreateDto) session.getAttribute("sale");
        session.removeAttribute("sale");

        Customer customer = this.customerRepository.findById(saleCreateDto.getCustomerId());
        Car car = this.carRepository.findById(saleCreateDto.getCarId());

        Sale sale = new Sale();
        sale.setCustomer(customer);
        sale.setCar(car);
        sale.setDiscount(saleCreateDto.getDiscount() / 100.0);
        sale.setDateRegistered(LocalDate.now());

        return this.saleRepository.save(sale).getId() != 0;
    }

    @Override
    public SaleDto prepareSale(SaleCreateDto saleCreateDto, HttpSession session) {
        Customer customer = this.customerRepository.findById(saleCreateDto.getCustomerId());
        CustomerDto customerDto = this.modelParser.convert(customer, CustomerDto.class);

        Car car = this.carRepository.findById(saleCreateDto.getCarId());
        CarDto carDto = this.modelParser.convert(car, CarDto.class);

        SaleDto saleDto = new SaleDto();
        saleDto.setCustomer(customerDto);
        saleDto.setCar(carDto);
        saleDto.setPrice(car.getPrice().add(this.getPartsPrice(car)));
        saleDto.setDiscountedPrice(
                this.getDiscountedPrice(saleDto, saleCreateDto.getDiscount() / 100.0, customer));
        saleDto.setDiscount(this.convertDiscountToString(saleCreateDto.getDiscount() / 100.0, customer));

        session.setAttribute("sale", saleCreateDto);
        return saleDto;
    }

    @Override
    public List<SaleDto> findAll() {
        List<Sale> sales = this.saleRepository.findAll();
        return this.mapSaleEntitiesToDtos(sales, SaleDto.class);
    }

    @Override
    public List<SaleDto> findAllOrderedByDate() {
        List<Sale> sales = this.saleRepository.findAllByOrderByDateRegisteredDesc();
        return this.mapSaleEntitiesToDtos(sales, SaleDto.class);
    }

    @Override
    public SaleDetailDto findById(long id) {
        Optional<Sale> optionalSale = this.saleRepository.findById(id);
        if (optionalSale.isPresent()) {
            Sale sale = optionalSale.get();
            return this.modelParser.convert(sale, SaleDetailDto.class);
        }

        return null;
    }

    @Override
    public List<SaleDto> findDiscountedSales() {
        List<Sale> discountedSales = this.saleRepository.findDiscountedSales();
        return this.mapSaleEntitiesToDtos(discountedSales, SaleDto.class);
    }

    @Override
    public List<SaleDtoWithPercent> findDiscountedSalesWithDiscountPercent(int percent) {
        List<Sale> discountedSalesWithPercent = this.saleRepository.findDiscountedSalesByDiscount(percent / 100.0);
        List<SaleDtoWithPercent> saleDtosWithPercent =
                this.mapSaleEntitiesToDtos(discountedSalesWithPercent, SaleDtoWithPercent.class);
        saleDtosWithPercent.forEach(e -> e.setPercent(percent));
        return saleDtosWithPercent;
    }

    private <T extends SaleDto> List<T> mapSaleEntitiesToDtos(List<Sale> sales, Class<T> saleDtoClass) {
        return sales.stream()
                .map(sale -> mapSaleEntityToDto(sale, saleDtoClass))
                .collect(Collectors.toList());
    }

    private <T extends SaleDto> T mapSaleEntityToDto(Sale sale, Class<T> saleDtoClass) {
        T saleDto = this.modelParser.convert(sale, saleDtoClass);
        BigDecimal partsPrice = this.getPartsPrice(sale.getCar());
        saleDto.setPrice(sale.getCar().getPrice().add(partsPrice));
        saleDto.setDiscountedPrice(this.getDiscountedPrice(saleDto, sale.getDiscount(), sale.getCustomer()));
        saleDto.setDiscount(this.convertDiscountToString(sale.getDiscount(), sale.getCustomer()));

        return saleDto;
    }

    private BigDecimal getPartsPrice(Car car) {
        return car.getParts().stream()
                .map(Part::getPrice)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    private BigDecimal getDiscountedPrice(SaleDto saleDto, double discount, Customer customer) {
        return saleDto.getPrice().multiply(
                new BigDecimal(1 - (discount + (customer.isYoungDriver() && discount <= 0.95 ? 0.05 : 0))))
                .setScale(2, RoundingMode.CEILING);
    }

    private String convertDiscountToString(double discount, Customer customer) {
        String discountString = (int) (discount * 100) + "%";
        if (customer.isYoungDriver() && discount <= 0.95) {
            discountString += " (+5%)";
        }

        return discountString;
    }

}
