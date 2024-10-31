package com.sunyesle.spring_boot_retrofit.user.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class UserListResponse {
    private Integer page;
    private Integer perPage;
    private Integer total;
    private Integer totalPages;
    private List<User> data;
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
