package com.sunyesle.spring_boot_jpa.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductRequest {
    private final String name;
    private final boolean enabled;
    private final ProductType productType;
}
