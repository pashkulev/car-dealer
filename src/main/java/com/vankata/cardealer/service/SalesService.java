package com.vankata.cardealer.service;

import com.vankata.cardealer.domain.dto.sale.SaleCreateDto;
import com.vankata.cardealer.domain.dto.sale.SaleDetailDto;
import com.vankata.cardealer.domain.dto.sale.SaleDto;
import com.vankata.cardealer.domain.dto.sale.SaleDtoWithPercent;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface SalesService {

    boolean addSale(HttpSession session);

    List<SaleDto> findAll();

    List<SaleDto> findAllOrderedByDate();

    SaleDetailDto findById(long id);

    List<SaleDto> findDiscountedSales();

    List<SaleDtoWithPercent> findDiscountedSalesWithDiscountPercent(int percent);

    SaleDto prepareSale(SaleCreateDto saleCreateDto, HttpSession session);
}
