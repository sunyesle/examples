package com.sunyesle.spring_boot_mybatis.enums;

import com.sunyesle.spring_boot_mybatis.infra.CodeEnum;

public enum OrderStatus implements CodeEnum {
    PENDING("1000"),
    READY("1001"),
    SHIPPING("1002"),
    SHIPPED("1003"),
    CANCELED("2000");

    private final String code;

    OrderStatus(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }
}
