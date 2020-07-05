package com.wirecard.util;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import java.util.ArrayList;
import java.util.List;

public class DozerConverter {

    private static final Mapper mapper = DozerBeanMapperBuilder.create().withMappingFiles("dozer-mapping.xml").build();

    public static <O, D> D parseObject (O origin, Class<D> destination){
        return mapper.map(origin, destination);
    }

    public static <O, D> List<D> parseObjectList(List<O> originObjectList, Class<D> destination){
        List<D> destinationObjectList = new ArrayList<D>();
        for (O originObject : originObjectList){
            destinationObjectList.add(parseObject(originObject, destination));
        }
        return destinationObjectList;
    }
}
