package com.vankata.cardealer.service;

import com.vankata.cardealer.domain.dto.car.CarDto;
import com.vankata.cardealer.domain.dto.car.CarDtoWithParts;

import java.util.List;

public interface CarService {

    List<CarDto> findAll();

    List<CarDto> findAllOrderedByBrandAndModel();

    List<CarDto> findByMake(String make);

    CarDtoWithParts findByIdWithParts(long id);

    boolean addCar(CarDto carDto);

    boolean addCarWithParts(CarDto cartDto, List<String> parts);

    List<String> getCarBrands();

    List<String> getBrandModels(String brand);
}
