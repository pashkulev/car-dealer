package com.vankata.cardealer.service;

import com.vankata.cardealer.domain.dto.part.FormPartDto;
import com.vankata.cardealer.domain.dto.part.PartDto;
import com.vankata.cardealer.domain.dto.part.ViewPartDto;
import com.vankata.cardealer.domain.entity.Part;
import com.vankata.cardealer.domain.entity.Supplier;
import com.vankata.cardealer.domain.mapper.ModelParser;
import com.vankata.cardealer.repository.PartRepository;
import com.vankata.cardealer.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class PartServiceImpl implements PartService {

    private final PartRepository partRepository;

    private final SupplierRepository supplierRepository;

    private final ModelParser modelParser;

    @Autowired
    public PartServiceImpl(PartRepository partRepository,
                           SupplierRepository supplierRepository,
                           ModelParser modelParser) {
        this.partRepository = partRepository;
        this.supplierRepository = supplierRepository;
        this.modelParser = modelParser;
    }

    @Override
    public FormPartDto findById(long id) {
        Optional<Part> optionalPart = this.partRepository.findById(id);
        if (optionalPart.isPresent()) {
            return this.modelParser.convert(optionalPart.get(), FormPartDto.class);
        }

        return null;
    }

    @Override
    public List<ViewPartDto> findAll() {
        List<Part> parts = this.partRepository.findAllByOrderByIdDesc();
        ViewPartDto[] viewPartDtos = this.modelParser.convert(parts, ViewPartDto[].class);
        return Arrays.asList(viewPartDtos);
    }


    @Override
    public List<PartDto> findBySupplierId(long supplierId) {
        List<Part> parts = this.partRepository.findBySupplier(supplierId);
        PartDto[] partDtos = this.modelParser.convert(parts, PartDto[].class);
        return Arrays.asList(partDtos);
    }

    @Override
    public List<FormPartDto> findByFormString(List<String> formStrings) {
        List<FormPartDto> formPartDtos = new ArrayList<>();
        for (String formString : formStrings) {
            long partId = Long.valueOf(formString.split(" ")[0]);
            Optional<Part> optionalPart = this.partRepository.findById(partId);
            if (optionalPart.isPresent()) {
                FormPartDto formPartDto = this.modelParser.convert(optionalPart.get(), FormPartDto.class);
                formPartDtos.add(formPartDto);
            }
        }

        return formPartDtos;
    }

    @Override
    public boolean addPart(FormPartDto partDto) {
        Optional<Supplier> optionalSupplier = this.supplierRepository.findById(partDto.getSupplierId());
        Part part = this.modelParser.convert(partDto, Part.class);
        optionalSupplier.ifPresent(part::setSupplier);
        return this.partRepository.save(part).getId() != 0;
    }

    @Override
    public void editPart(FormPartDto partDto) {
        Optional<Supplier> optionalSupplier = this.supplierRepository.findById(partDto.getSupplierId());
        Part part = this.modelParser.convert(partDto, Part.class);
        optionalSupplier.ifPresent(part::setSupplier);
        this.partRepository.save(part);
    }

    @Override
    public void deleteById(long id) {
        this.partRepository.deleteById(id);
    }
}
