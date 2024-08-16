package com.sunyesle.spring_boot_jpa.global.config;

import com.p6spy.engine.spy.P6SpyOptions;
import jakarta.annotation.PostConstruct;

//@Configuration
public class P6spyConfig {

    @PostConstruct
    public void setLogMessageFormat() {
        // spy.properties 대신 JavaConfig를 통해 설정할 수도 있다.
        P6SpyOptions.getActiveInstance().setLogMessageFormat(P6spyPrettySqlFormat.class.getName());
    }
}
