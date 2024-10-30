package com.sunyesle.spring_boot_retrofit.user;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserAPI {

    @GET("users/{userId}")
    Call<UserResponse> getUser(@Path("userId") Integer userId);
}
