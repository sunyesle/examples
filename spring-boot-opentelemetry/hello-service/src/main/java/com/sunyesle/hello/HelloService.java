package com.sunyesle.hello;

import com.sunyesle.hello.greeting.GreetingServiceClient;
import com.sunyesle.hello.user.User;
import com.sunyesle.hello.user.UserServiceClient;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class HelloService {
    private final UserServiceClient userServiceClient;
    private final GreetingServiceClient greetingServiceClient;

    public HelloService(UserServiceClient userServiceClient, GreetingServiceClient greetingServiceClient) {
        this.userServiceClient = userServiceClient;
        this.greetingServiceClient = greetingServiceClient;
    }

    public String sayHello(Locale locale, long userId) {
        User user = userServiceClient.find(userId);
        String greeting = greetingServiceClient.greeting(locale);
        return greeting + " " + user.name();
    }
}
