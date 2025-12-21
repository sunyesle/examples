package com.sunyesle.hello;

import com.sunyesle.hello.greeting.GreetingServiceHttpClient;
import com.sunyesle.hello.user.UserServiceHttpClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.service.registry.ImportHttpServices;

@Configuration(proxyBeanMethods = false)
@ImportHttpServices(group = "user", types = UserServiceHttpClient.class)
@ImportHttpServices(group = "greeting", types = GreetingServiceHttpClient.class)
public class HttpServiceClientConfig {
}
