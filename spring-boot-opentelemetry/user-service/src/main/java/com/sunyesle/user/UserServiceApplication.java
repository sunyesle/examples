package com.sunyesle.user;

import com.sunyesle.shared.FilterConfig;
import com.sunyesle.shared.OpenTelemetryConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({OpenTelemetryConfig.class, FilterConfig.class})
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

}
