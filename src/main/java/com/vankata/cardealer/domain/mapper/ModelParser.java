package com.vankata.cardealer.domain.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

public interface ModelParser {

    <S, D> D convert(S source, Class<D> destinationClass);

    <S, D> D convert(S source, Class<D> destinationClass, PropertyMap<S, D> propertyMap);

    ModelMapper getModelMapper();
}
