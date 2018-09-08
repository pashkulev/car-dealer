package com.vankata.cardealer.repository;

import com.vankata.cardealer.domain.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    @Query("SELECT s FROM Supplier AS s WHERE s.isImporter = false")
    List<Supplier> findLocalSupplires();

    @Query("SELECT s FROM Supplier AS s WHERE s.isImporter = true")
    List<Supplier> findImporters();
}
