package com.sunyesle.spring_boot_api_versioning.user;

public record UserDTOv1 (
        Integer id,
        String name,
        String email
) {
}
