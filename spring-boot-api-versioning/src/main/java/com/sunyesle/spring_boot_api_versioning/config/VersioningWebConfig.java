package com.sunyesle.spring_boot_api_versioning.config;

import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ApiVersionConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Java Config 예시
//@Configuration
@Profile("versioning")
public class VersioningWebConfig implements WebMvcConfigurer {

    @Override
    public void configureApiVersioning(ApiVersionConfigurer configurer) {
        configurer
                .addSupportedVersions("1.0", "2.0")
                .setDefaultVersion("1.0")
                .useRequestHeader("X-API-Version")
                .useQueryParam("version")
                .useMediaTypeParameter(MediaType.APPLICATION_JSON, "version")
        ;
    }
}
