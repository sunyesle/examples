package com.sunyesle.spring_boot_retrofit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserCaller {
    private final UserAPI userAPI;

    public UserResponse getUsers(Integer userId) {
        Call<UserResponse> call = userAPI.getUser(userId);
        try {
            Response<UserResponse> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            }
            log.error(response.errorBody().string());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return new UserResponse();
    }
}
