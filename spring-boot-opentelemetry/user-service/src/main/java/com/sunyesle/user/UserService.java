package com.sunyesle.user;

import io.micrometer.observation.annotation.ObservationKeyValue;
import io.micrometer.observation.annotation.Observed;
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

    @Observed(name = "user.create")
    @Transactional
    public User create(String name) {
        LOGGER.info("Creating user '{}'", name);
        return userRepository.save(new User(null, name));
    }

    @Observed(name = "user.list-all")
    @Transactional(readOnly = true)
    public List<User> listAll() {
        LOGGER.info("Listing all users");
        return userRepository.findAll();
    }

    @Observed(name = "user.find-with-id")
    @Transactional(readOnly = true)
    public @Nullable User findWithId(@ObservationKeyValue("id") long id) {
        LOGGER.info("Finding user with id {}", id);
        return userRepository.findById(id).orElse(null);
    }
}
