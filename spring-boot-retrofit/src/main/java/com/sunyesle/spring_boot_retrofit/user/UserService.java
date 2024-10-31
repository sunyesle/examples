package com.sunyesle.spring_boot_retrofit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserCaller userCaller;

    public UserDto getUser(Integer userId) {
        UserResponse user = userCaller.getUsers(userId)
                .orElseThrow(NoSuchElementException::new);

        return new UserDto(
                user.getData().getId(),
                user.getData().getEmail(),
                user.getData().getFirstName(),
                user.getData().getLastName(),
                user.getData().getAvatar()
        );
    }
}
