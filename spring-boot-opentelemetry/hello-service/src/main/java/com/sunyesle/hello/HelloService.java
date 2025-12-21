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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class HelloService {
    private static final Logger LOGGER = LoggerFactory.getLogger(HelloService.class);

    private final UserServiceClient userServiceClient;
    private final GreetingServiceClient greetingServiceClient;
    private final HelloConfigProperties helloConfigProperties;

    public HelloService(UserServiceClient userServiceClient, GreetingServiceClient greetingServiceClient, HelloConfigProperties helloConfigProperties) {
        this.userServiceClient = userServiceClient;
        this.greetingServiceClient = greetingServiceClient;
        this.helloConfigProperties = helloConfigProperties;
    }

    @Observed(name = "say-hello")
    public String sayHello(@ObservationKeyValue("locale") Locale locale, @ObservationKeyValue("user.id") long userId) {
        LOGGER.info("Saying hello to user {} with locale {}", userId, locale);
        if (helloConfigProperties.isAsync()) {
            return sayHelloAsync(locale, userId);
        }
        User user = userServiceClient.find(userId);
        String greeting = greetingServiceClient.greeting(locale);
        return greeting + " " + user.name();
    }

    private String sayHelloAsync(Locale locale, long userId) {
        Future<User> user = userServiceClient.findAsync(userId);
        Future<String> greeting = greetingServiceClient.greetingAsync(locale);
        try {
            return greeting.get() + " " + user.get().name();
        } catch (ExecutionException e) {
            if (e.getCause() instanceof RuntimeException runtimeException) {
                throw runtimeException;
            }
            throw new RuntimeException("Fetching greeting or user failed", e);
        } catch (InterruptedException e) {
            throw new RuntimeException("Got interrupted while waiting for greeting or user", e);
        }
    }
}
