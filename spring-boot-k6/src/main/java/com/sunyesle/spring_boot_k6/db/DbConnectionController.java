package com.sunyesle.spring_boot_k6.db;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/conn")
@RequiredArgsConstructor
public class DbConnectionController {

    private final ItemRepository repository;

    // DB 커넥션을 5초 동안 붙잡고 있는 API
    @GetMapping("/leak")
    @Transactional
    public String slowQuery() {
        repository.findById(1L);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return "Success";
    }
}
