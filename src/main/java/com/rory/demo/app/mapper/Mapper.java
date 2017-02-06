package com.rory.demo.app.mapper;

import java.util.List;

public interface Mapper<T, U> {

    U convertToDTO(T t);

    T convertFromDTO(U u);

    List<U> convertToDTO(List<T> t);

    List<T> convertFromDTO(List<U> u);
}
