package com.sunyesle.spring_boot_api_versioning.config;

import org.springframework.web.servlet.config.annotation.ApiVersionConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Java Config 예시
//@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configureApiVersioning(ApiVersionConfigurer configurer) {
        configurer
                .addSupportedVersions("1.0", "2.0")
                .usePathSegment(1)
                .useRequestHeader("X-API-Version")
        ;
    }
}
