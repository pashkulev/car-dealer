package com.vankata.cardealer.domain.dto.car;

import com.vankata.cardealer.domain.dto.part.SimplePartDto;

import java.util.ArrayList;
import java.util.List;

public class CarDtoWithParts extends CarDto {

    private List<SimplePartDto> parts;

    public CarDtoWithParts() {
        super();
        this.parts = new ArrayList<>();
    }

    public List<SimplePartDto> getParts() {
        return this.parts;
    }

    public void setParts(List<SimplePartDto> parts) {
        this.parts = parts;
    }
}
