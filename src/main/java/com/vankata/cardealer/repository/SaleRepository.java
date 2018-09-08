package com.vankata.cardealer.repository;

import com.vankata.cardealer.domain.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    List<Sale> findAllByOrderByDateRegisteredDesc();

    @Query("SELECT s FROM Sale AS s WHERE s.discount > 0 " +
            "ORDER BY s.dateRegistered DESC")
    List<Sale> findDiscountedSales();

    @Query("SELECT s FROM Sale AS s WHERE s.discount > 0 AND s.discount = :discount " +
            "ORDER BY s.dateRegistered")
    List<Sale> findDiscountedSalesByDiscount(@Param("discount") double discount);
}
