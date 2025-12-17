package com.sunyesle.spring_boot_api_versioning.user;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Profile("versioning")
public class UserVersioningController {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @GetMapping(version = "1.0")
    public List<UserDTOv1> getV1() {
        return userRepository.findAll().stream()
                .map(userMapper::toV1)
                .toList();
    }

    @GetMapping(version = "2.0")
    public List<UserDTOv2> getV2() {
        return userRepository.findAll().stream()
                .map(userMapper::toV2)
                .toList();
    }
}

