package com.sunyesle.spring_boot_jpa_querydsl.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MemberDtoConstructor {
    private String name;
    private Integer age;

    public MemberDtoConstructor(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}
