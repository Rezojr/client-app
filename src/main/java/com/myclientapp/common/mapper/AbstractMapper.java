package com.myclientapp.common.mapper;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public interface AbstractMapper<E, T> {

    T toDto(E entity);

    E toEntity(T dto);

    default Page<T> mapAll(Page<E> in) {
        return in.map(this::toDto);
    }

    default List<T> mapAll(List<E> in) {
        return in.stream().map(this::toDto).collect(Collectors.toList());
    }

}
