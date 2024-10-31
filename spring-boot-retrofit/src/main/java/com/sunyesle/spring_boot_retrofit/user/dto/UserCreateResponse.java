package com.sunyesle.spring_boot_retrofit.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserCreateResponse {
    private String name;
    private String job;
    private Integer id;
    @JsonProperty("createdAt")
    private LocalDateTime createdAt;
}
