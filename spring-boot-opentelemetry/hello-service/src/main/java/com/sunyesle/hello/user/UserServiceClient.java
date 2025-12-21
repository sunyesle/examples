package com.sunyesle.hello.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

@Component
public class UserServiceClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceClient.class);

    private final UserServiceHttpClient httpClient;

    public UserServiceClient(UserServiceHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public User find(long id) {
        LOGGER.debug("Fetching user with id {}", id);
        return findImpl(id);
    }

    private User findImpl(long id) {
        try {
            return httpClient.find(id);
        } catch (HttpClientErrorException.NotFound ex) {
            throw new UserNotFoundException(id, ex);
        }
    }
}
