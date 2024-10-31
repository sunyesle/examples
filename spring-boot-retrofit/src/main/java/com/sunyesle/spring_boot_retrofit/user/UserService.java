package com.sunyesle.spring_boot_retrofit.user;

import com.sunyesle.spring_boot_retrofit.user.dto.UserCreateRequest;
import com.sunyesle.spring_boot_retrofit.user.dto.UserCreateResponse;
import com.sunyesle.spring_boot_retrofit.user.dto.UserListResponse;
import com.sunyesle.spring_boot_retrofit.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserCaller userCaller;

    public UserListResponse getUserList(Integer page) {
        return userCaller.getUserList(page)
                .orElseThrow(NoSuchElementException::new);
    }

    public UserResponse getUser(Integer userId) {
        return userCaller.getUser(userId)
                .orElseThrow(NoSuchElementException::new);
    }

    public UserCreateResponse createUser(UserCreateRequest request) {
        return userCaller.createUser(request)
                .orElseThrow(RuntimeException::new);
    }
}
