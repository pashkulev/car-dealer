package com.vankata.cardealer.domain.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class ModelParserImpl implements ModelParser {

    private ModelMapper modelMapper;

    public ModelParserImpl() {
        this.modelMapper = new ModelMapper();
    }

    @Override
    public <S, D> D convert(S source, Class<D> destinationClass) {
        return this.modelMapper.map(source, destinationClass);
    }

    @Override
    public <S, D> D convert(S source, Class<D> destinationClass, PropertyMap<S, D> propertyMap) {
        this.modelMapper = new ModelMapper();
        this.modelMapper.addMappings(propertyMap);
        return this.modelMapper.map(source, destinationClass);
    }

    @Override
    public ModelMapper getModelMapper() {
        return this.modelMapper;
    }
}
