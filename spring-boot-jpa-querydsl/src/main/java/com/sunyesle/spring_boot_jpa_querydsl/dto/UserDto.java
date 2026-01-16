package com.sunyesle.spring_boot_jpa_querydsl.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserDto {
    private String username;
    private Integer age;
}
