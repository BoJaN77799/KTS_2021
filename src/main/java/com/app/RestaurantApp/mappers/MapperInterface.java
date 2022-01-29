package com.app.RestaurantApp.mappers;

public interface MapperInterface<T,U> {

    T toEntity(U dto);

    U toDto(T entity);
}
