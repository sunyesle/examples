package com.sunyesle.hello;

import com.sunyesle.hello.greeting.GreetingServiceClient;
import com.sunyesle.hello.user.User;
import com.sunyesle.hello.user.UserServiceClient;
import io.micrometer.observation.annotation.ObservationKeyValue;
import io.micrometer.observation.annotation.Observed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class HelloService {
    private static final Logger LOGGER = LoggerFactory.getLogger(HelloService.class);

    private final UserServiceClient userServiceClient;
    private final GreetingServiceClient greetingServiceClient;

    public HelloService(UserServiceClient userServiceClient, GreetingServiceClient greetingServiceClient) {
        this.userServiceClient = userServiceClient;
        this.greetingServiceClient = greetingServiceClient;
    }

    @Observed(name = "say-hello")
    public String sayHello(@ObservationKeyValue("locale") Locale locale, @ObservationKeyValue("user.id") long userId) {
        LOGGER.info("Saying hello to user {} with locale {}", userId, locale);
        User user = userServiceClient.find(userId);
        String greeting = greetingServiceClient.greeting(locale);
        return greeting + " " + user.name();
    }
}
