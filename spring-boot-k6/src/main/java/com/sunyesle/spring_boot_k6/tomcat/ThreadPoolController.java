package com.sunyesle.spring_boot_k6.tomcat;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/api/tomcat")
public class ThreadPoolController {

    // 톰캣 스레드를 5초 동안 붙잡고 있는 API
    @GetMapping("/heavy")
    public String heavyRequest() {
        log.info("Heavy request started: {}", Thread.currentThread().getName());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        log.info("Heavy request finished: {}", Thread.currentThread().getName());
        return "Heavy Task Done";
    }

    // 즉시 응답해야 하는 API
    @GetMapping("/light")
    public String lightRequest() {
        log.info("Light request processed: {}", Thread.currentThread().getName());
        return "Light Task Done";
    }
}
