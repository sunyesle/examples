package com.sunyesle.spring_boot_cache.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductRequest {
    private final String name;
    private final Integer price;

    public Product toEntity() {
        return new Product(name, price);
    }
}
