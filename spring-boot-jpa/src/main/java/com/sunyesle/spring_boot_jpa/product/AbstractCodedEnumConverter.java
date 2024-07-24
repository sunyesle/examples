package com.sunyesle.spring_boot_jpa.product;

import jakarta.persistence.AttributeConverter;

import java.util.Arrays;

public abstract class AbstractCodedEnumConverter<T extends Enum<T> & CodedEnum<E>, E> implements AttributeConverter<T, E> {
    private final Class<T> clazz;

    protected AbstractCodedEnumConverter(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public E convertToDatabaseColumn(T attribute) {
        return attribute.getCode();
    }

    @Override
    public T convertToEntityAttribute(E dbData) {
        return Arrays.stream(clazz.getEnumConstants())
                .filter(v -> v.getCode().equals(dbData))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Unknown code: " + dbData));
    }
}
