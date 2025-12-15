package com.sunyesle.spring_boot_api_versioning.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @GetMapping(path = "/v{version}/users", version = "1.0")
    public List<UserDTOv1> getPathV1() {
        return userRepository.findAll().stream()
                .map(userMapper::toV1)
                .toList();
    }

    @GetMapping(path = "/v{version}/users", version = "2.0")
    public List<UserDTOv2> getPathV2() {
        return userRepository.findAll().stream()
                .map(userMapper::toV2)
                .toList();
    }
}

