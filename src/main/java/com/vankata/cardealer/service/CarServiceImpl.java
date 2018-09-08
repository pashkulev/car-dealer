package com.vankata.cardealer.service;

import com.vankata.cardealer.domain.dto.car.CarDto;
import com.vankata.cardealer.domain.dto.car.CarDtoWithParts;
import com.vankata.cardealer.domain.entity.Car;
import com.vankata.cardealer.domain.entity.Part;
import com.vankata.cardealer.domain.mapper.ModelParser;
import com.vankata.cardealer.repository.CarRepository;
import com.vankata.cardealer.repository.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final PartRepository partRepository;
    private final ModelParser modelParser;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, PartRepository partRepository, ModelParser modelParser) {
        this.carRepository = carRepository;
        this.partRepository = partRepository;
        this.modelParser = modelParser;
    }

    @Override
    public List<CarDto> findAll() {
        List<Car> cars = this.carRepository.findAllByOrderByIdDesc();
        CarDto[] carDtos = this.modelParser.convert(cars, CarDto[].class);
        return Arrays.asList(carDtos);
    }

    @Override
    public List<CarDto> findAllOrderedByBrandAndModel() {
        List<Car> cars = this.carRepository.findAllByOrderByMakeAscModelAsc();
        CarDto[] carDtos = this.modelParser.convert(cars, CarDto[].class);
        return Arrays.asList(carDtos);
    }

    @Override
    public List<CarDto> findByMake(String make) {
        List<Car> cars = this.carRepository.findAllByMakeOrderByModelAscTravelledDistanceDesc(make);
        CarDto[] carDtos = this.modelParser.convert(cars, CarDto[].class);
        return Arrays.asList(carDtos);
    }

    @Override
    public CarDtoWithParts findByIdWithParts(long id) {
        Car car = this.carRepository.findById(id);
        return this.modelParser.convert(car, CarDtoWithParts.class);
    }

    @Override
    public boolean addCar(CarDto carDto) {
        Car car = this.modelParser.convert(carDto, Car.class);
        return this.carRepository.save(car).getId() != 0;
    }

    @Override
    public boolean addCarWithParts(CarDto carDto, List<String> parts) {
        Car car = this.modelParser.convert(carDto, Car.class);

        if (parts != null && !parts.isEmpty()) {
            for (String partString : parts) {
                long id = Long.valueOf(partString.split(" ")[0]);
                Optional<Part> optionalPart = this.partRepository.findById(id);

                if (optionalPart.isPresent()) {
                    Part part = optionalPart.get();
                    car.getParts().add(part);
                }
            }
        }

        return this.carRepository.save(car).getId() != 0;
    }

    @Override
//    @Cacheable("carBrands")
    public List<String> getCarBrands() {
        return this.carRepository.getDistinctByMake();
    }

    @Override
    public List<String> getBrandModels(String brand) {
        return this.carRepository.getDistinctModelsByMake(brand);
    }
}
