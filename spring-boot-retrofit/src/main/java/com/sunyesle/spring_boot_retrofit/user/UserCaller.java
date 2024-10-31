package com.sunyesle.spring_boot_retrofit.user;

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

    public Optional<UserResponse> getUsers(Integer userId) {
        return RetrofitUtil.requestSync(userAPI.getUser(userId));
    }
}
