package com.sunyesle.user;

import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User create(String name) {
        LOGGER.info("Creating user '{}'", name);
        return userRepository.save(new User(null, name));
    }

    @Transactional(readOnly = true)
    public List<User> listAll() {
        LOGGER.info("Listing all users");
        return userRepository.findAll();
    }

    public @Nullable User findWithId(long id) {
        LOGGER.info("Finding user with id {}", id);
        return userRepository.findById(id).orElse(null);
    }
}
