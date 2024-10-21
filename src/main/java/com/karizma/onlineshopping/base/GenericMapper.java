package com.karizma.onlineshopping.base;

import java.util.List;

public interface GenericMapper<T, X> {
    X toDTO(T var1);

    T toEntity(X var1);

    List<X> toDtoList(List<T> var1);

    List<T> toEntityList(List<X> var1);
}
