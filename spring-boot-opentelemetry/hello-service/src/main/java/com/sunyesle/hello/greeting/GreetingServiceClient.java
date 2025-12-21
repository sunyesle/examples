package com.sunyesle.hello.greeting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.concurrent.Future;

@Component
public class GreetingServiceClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(GreetingServiceClient.class);

    private final GreetingServiceHttpClient httpClient;
    private final AsyncTaskExecutor asyncTaskExecutor;

    public GreetingServiceClient(GreetingServiceHttpClient httpClient, AsyncTaskExecutor asyncTaskExecutor) {
        this.httpClient = httpClient;
        this.asyncTaskExecutor = asyncTaskExecutor;
    }

    public String greeting(Locale locale) {
        LOGGER.debug("Fetching greeting for locale {}", locale);
        return greetingImpl(locale);
    }

    public Future<String> greetingAsync(Locale locale) {
        LOGGER.debug("Fetching greeting async for locale {}", locale);
        return asyncTaskExecutor.submit(() -> greetingImpl(locale));
    }

    private String greetingImpl(Locale locale) {
        return this.httpClient.greeting(locale.toLanguageTag());
    }
}

