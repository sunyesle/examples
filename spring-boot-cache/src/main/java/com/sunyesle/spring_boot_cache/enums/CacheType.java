package com.sunyesle.spring_boot_cache.enums;

import lombok.Getter;

@Getter
public enum CacheType {
    PRODUCTS("products", 5 * 60, 10000),
    PRODUCT_LIST("product-list", 60, 1000);

    CacheType(String cacheName, int expireAfterWrite, int maximumSize) {
        this.cacheName = cacheName;
        this.expireAfterWrite = expireAfterWrite;
        this.maximumSize = maximumSize;
    }

    private final String cacheName;
    private final int expireAfterWrite;
    private final int maximumSize;
}
