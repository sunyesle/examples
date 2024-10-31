package com.sunyesle.spring_boot_retrofit.user;

import com.sunyesle.spring_boot_retrofit.user.dto.UserCreateRequest;
import com.sunyesle.spring_boot_retrofit.user.dto.UserCreateResponse;
import com.sunyesle.spring_boot_retrofit.user.dto.UserListResponse;
import com.sunyesle.spring_boot_retrofit.user.dto.UserResponse;
import retrofit2.Call;
import retrofit2.http.*;

public interface UserAPI {

    @GET("users")
    Call<UserListResponse> getUserList(@Query("page") Integer page);

    @GET("users/{userId}")
    Call<UserResponse> getUser(@Path("userId") Integer userId);

    @POST("users")
    Call<UserCreateResponse> saveUser(@Body UserCreateRequest request);
}
