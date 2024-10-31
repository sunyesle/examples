package com.sunyesle.spring_boot_retrofit.user;

import com.sunyesle.spring_boot_retrofit.user.dto.UserCreateRequest;
import com.sunyesle.spring_boot_retrofit.user.dto.UserCreateResponse;
import com.sunyesle.spring_boot_retrofit.user.dto.UserListResponse;
import com.sunyesle.spring_boot_retrofit.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserListResponse> getUserList(@RequestParam(defaultValue = "1") Integer page) {
        return ResponseEntity.ok(userService.getUserList(page));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @PostMapping
    public ResponseEntity<UserCreateResponse> createUser(@RequestBody UserCreateRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.createUser(request));
    }
}
