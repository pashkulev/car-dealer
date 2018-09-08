package com.vankata.cardealer.service;

import com.vankata.cardealer.domain.dto.supplier.SupplierDto;
import com.vankata.cardealer.domain.dto.supplier.SupplierDtoWithPartsCount;
import com.vankata.cardealer.domain.entity.Supplier;
import com.vankata.cardealer.domain.mapper.ModelParser;
import com.vankata.cardealer.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final ModelParser modelParser;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository, ModelParser modelParser) {
        this.supplierRepository = supplierRepository;
        this.modelParser = modelParser;
    }

    @Override
    public boolean addSupplier(SupplierDto supplierDto) {
        Supplier supplier = this.modelParser.convert(supplierDto, Supplier.class);
        return this.supplierRepository.save(supplier).getId() != 0;
    }

    @Override
    public void editSupplier(SupplierDto supplierDto) {
        Supplier supplier = this.modelParser.convert(supplierDto, Supplier.class);
        this.supplierRepository.save(supplier);
    }

    @Override
    public void deleteById(long id) {
        this.supplierRepository.deleteById(id);
    }

    @Override
    public SupplierDto findById(long id) {
        Optional<Supplier> optionalSupplier = this.supplierRepository.findById(id);
        if (optionalSupplier.isPresent()) {
            return this.modelParser.convert(optionalSupplier.get(), SupplierDto.class);
        }

        return null;
    }

    @Override
//    @Cacheable("suppliers")
    public List<SupplierDtoWithPartsCount> findAll() {
        List<Supplier> suppliers = this.supplierRepository.findAll();
        return this.mapSupplierEntitiesToDtos(suppliers);
    }

    @Override
    public List<SupplierDtoWithPartsCount> findLocalSuppliers() {
        List<Supplier> suppliers = this.supplierRepository.findLocalSupplires();
        return this.mapSupplierEntitiesToDtos(suppliers);
    }

    @Override
    public List<SupplierDtoWithPartsCount> findImporters() {
        List<Supplier> suppliers = this.supplierRepository.findImporters();
        return this.mapSupplierEntitiesToDtos(suppliers);
    }

    @Override
    public List<SupplierDto> findAllSimpleSuppliers() {
        List<Supplier> suppliers = this.supplierRepository.findAll();
        SupplierDto[] simpleSupplierDtos = this.modelParser.convert(suppliers, SupplierDto[].class);
        return Arrays.asList(simpleSupplierDtos);
    }

    private List<SupplierDtoWithPartsCount> mapSupplierEntitiesToDtos(List<Supplier> suppliers) {
        List<SupplierDtoWithPartsCount> supplierDtos = new ArrayList<>();

        for (Supplier supplier : suppliers) {
            SupplierDtoWithPartsCount supplierDto = new SupplierDtoWithPartsCount();
            supplierDto.setId(supplier.getId());
            supplierDto.setName(supplier.getName());
            supplierDto.setIsImporter(supplier.getIsImporter());
            supplierDto.setPartsCount(supplier.getParts().size());

            supplierDtos.add(supplierDto);
        }

        return supplierDtos;
    }
}
