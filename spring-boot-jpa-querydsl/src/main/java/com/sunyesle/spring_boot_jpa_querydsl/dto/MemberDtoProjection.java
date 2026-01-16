package com.sunyesle.spring_boot_jpa_querydsl.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MemberDtoProjection {
    private String name;
    private Integer age;

    @QueryProjection
    public MemberDtoProjection(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}
