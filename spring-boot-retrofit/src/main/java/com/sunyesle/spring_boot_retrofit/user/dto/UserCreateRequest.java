package com.sunyesle.spring_boot_retrofit.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserCreateRequest {
    private final String name;
    private final String job;
}
