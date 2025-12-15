package com.sunyesle.spring_boot_api_versioning.user;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {
    private List<User> users = new ArrayList<>();

    public List<User> findAll(){
        return users;
    }

    @PostConstruct
    private void init() {
        users.add(new User(1, "Harry Potter", "harrypotter@example.com"));
    }
}
