package com.sunyesle.spring_boot_mybatis.infra;

import java.util.EnumSet;

public interface CodeEnum {
    String getCode();

    static <E extends Enum<E> & CodeEnum> E fromCode(Class<E> type, String code){
        return EnumSet.allOf(type)
                .stream()
                .filter(codeEnum -> codeEnum.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid code value '" + code + "' for enum type " + type.getSimpleName()));
    }
}
