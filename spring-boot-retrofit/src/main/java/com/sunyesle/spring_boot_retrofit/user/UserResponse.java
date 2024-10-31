package com.sunyesle.spring_boot_retrofit.user;

import lombok.Getter;

@Getter
public class UserResponse {
    private User data;
    private Support support;

    @Getter
    public static class User {
        private Integer id;
        private String email;
        private String firstName;
        private String lastName;
        private String avatar;
    }

    @Getter
    public static class Support {
        private String url;
        private String text;
    }
}
