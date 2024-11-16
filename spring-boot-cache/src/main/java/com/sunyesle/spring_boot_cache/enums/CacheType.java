package com.sunyesle.spring_boot_cache.enums;

import lombok.Getter;

@Getter
public enum CacheType {
    PRODUCTS("products", 5 * 60, 10000);

    CacheType(String cacheName, int expiredAfterWrite, int maximumSize) {
        this.cacheName = cacheName;
        this.expiredAfterWrite = expiredAfterWrite;
        this.maximumSize = maximumSize;
    }

    private final String cacheName;
    private final int expiredAfterWrite;
    private final int maximumSize;
}
