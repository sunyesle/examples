package com.sunyesle.spring_boot_api_versioning.user;

public record User(
        Integer id,
        String name,
        String email
) {
}
