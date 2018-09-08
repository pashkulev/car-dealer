package com.vankata.cardealer.service;

import com.vankata.cardealer.domain.dto.part.FormPartDto;
import com.vankata.cardealer.domain.dto.part.PartDto;
import com.vankata.cardealer.domain.dto.part.ViewPartDto;

import java.util.List;

public interface PartService {

    FormPartDto findById(long id);

    List<ViewPartDto> findAll();

    List<PartDto> findBySupplierId(long supplierId);

    List<FormPartDto> findByFormString(List<String> formStrings);

    boolean addPart(FormPartDto partDto);

    void editPart(FormPartDto partDto);

    void deleteById(long id);
}
