package com.sunyesle.spring_boot_retrofit.user;

import com.sunyesle.spring_boot_retrofit.user.dto.UserCreateRequest;
import com.sunyesle.spring_boot_retrofit.user.dto.UserCreateResponse;
import com.sunyesle.spring_boot_retrofit.user.dto.UserListResponse;
import com.sunyesle.spring_boot_retrofit.user.dto.UserResponse;
import com.sunyesle.spring_boot_retrofit.util.RetrofitUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserCaller {
    private final UserAPI userAPI;

    public Optional<UserListResponse> getUserList(Integer page) {
        return RetrofitUtil.requestSync(userAPI.getUserList(page));
    }

    public Optional<UserResponse> getUser(Integer userId) {
        return RetrofitUtil.requestSync(userAPI.getUser(userId));
    }

    public Optional<UserCreateResponse> createUser(UserCreateRequest request) {
        return RetrofitUtil.requestSync(userAPI.saveUser(request));
    }
}
