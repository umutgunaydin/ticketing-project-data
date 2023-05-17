package com.company.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MapperUtil {

    private final ModelMapper modelMapper;

    public MapperUtil(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public <T> T convert(Object objectToBeConverted,Class<T> convertedObject){
        return modelMapper.map(objectToBeConverted, convertedObject);
    }

}
