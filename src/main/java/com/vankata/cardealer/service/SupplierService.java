package com.vankata.cardealer.service;

import com.vankata.cardealer.domain.dto.supplier.SupplierDto;
import com.vankata.cardealer.domain.dto.supplier.SupplierDtoWithPartsCount;

import java.util.List;

public interface SupplierService {

    boolean addSupplier(SupplierDto supplierDto);

    void editSupplier(SupplierDto supplierDto);

    void deleteById(long id);

    SupplierDto findById(long id);

    List<SupplierDto> findAllSimpleSuppliers();

    List<SupplierDtoWithPartsCount> findAll();

    List<SupplierDtoWithPartsCount> findLocalSuppliers();

    List<SupplierDtoWithPartsCount> findImporters();
}
