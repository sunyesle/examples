package com.sunyesle.spring_boot_k6.cpu;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/api/cpu")
public class CpuContentionController {

    private final Object lock = new Object();

    @GetMapping("/contention")
    public String simpleCpuTask() {
        long startTime = System.currentTimeMillis();

        // 300밀리초 동안 CPU 코어 하나를 점유 한다.
        while (System.currentTimeMillis() - startTime < 300) {
            double value = ThreadLocalRandom.current().nextDouble();
            double result = Math.sin(value) * Math.cos(value);
        }
        return "CPU Task Done";
    }
}
