package com.sunyesle.hello.greeting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class GreetingServiceClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(GreetingServiceClient.class);

    private final GreetingServiceHttpClient httpClient;

    public GreetingServiceClient(GreetingServiceHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public String greeting(Locale locale) {
        LOGGER.debug("Fetching greeting for locale {}", locale);
        return greetingImpl(locale);
    }

    private String greetingImpl(Locale locale) {
        return this.httpClient.greeting(locale.toLanguageTag());
    }
}

