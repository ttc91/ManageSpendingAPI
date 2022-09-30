package com.example.managespending.data.mapper.base;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractModelMapper <E, D>{

    @Autowired
    ModelMapper mapper;

    public D mapToDTO (E e, Class<D> d) {
        return e != null ? mapper.map(e, d) : null;
    }

    public E mapToEntity(D d, Class<E> e){
        return d != null ? mapper.map(d, e) : null;
    }

    public List<D> mapToDTOList (List<E> eList, Class<D> d){
        return eList != null ? eList.stream().map(e -> mapper.map(e, d)).collect(Collectors.toList()) : null;
    }

    public List<E> mapToEntityList (List<D> dList, Class<E> e){
        return dList != null ? dList.stream().map(d -> mapper.map(d, e)).collect(Collectors.toList()) : null;
    }

}
