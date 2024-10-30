package com.sunyesle.spring_boot_retrofit.user;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class UserResponse {
    private User data;
    private Support support;

    @Getter
    public static class User {
        private Integer id;
        private String email;

        @SerializedName("first_name")
        private String firstName;

        @SerializedName("last_name")
        private String lastName;

        private String avatar;
    }

    @Getter
    public static class Support {
        private String url;
        private String text;
    }
}
