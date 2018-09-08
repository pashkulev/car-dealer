package com.vankata.cardealer.repository;

import com.vankata.cardealer.domain.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartRepository extends JpaRepository<Part, Long> {

    List<Part> findAllByOrderByIdDesc();

    @Query("SELECT p FROM Part AS p WHERE p.supplier.id = :id")
    List<Part> findBySupplier(@Param("id") long supplierId);
}
