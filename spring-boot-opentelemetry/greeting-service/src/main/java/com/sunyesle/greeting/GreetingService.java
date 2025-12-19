package com.sunyesle.greeting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Map;

@Service
public class GreetingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GreetingService.class);

    private final Map<Locale, String> greetings = Map.of(
            Locale.ENGLISH, "Hello",
            Locale.GERMAN, "Hallo",
            Locale.KOREAN, "안녕"
    );

    public String getGreeting(Locale locale) {
        LOGGER.info("Looking up greeting for locale {}", locale);
        String greeting = greetings.get(locale);
        if(greeting == null) {
            greeting = greetings.get(Locale.of(locale.getLanguage()));
        }
        return (greeting != null) ? greeting : this.greetings.get(Locale.ENGLISH);
    }
}
