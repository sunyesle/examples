package com.sunyesle.spring_boot_api_versioning.user;

public record UserDTOv2(
        Integer id,
        String firstName,
        String lastName,
        String email
) {
}
