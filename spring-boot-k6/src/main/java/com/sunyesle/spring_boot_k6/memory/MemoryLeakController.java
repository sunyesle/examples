package com.sunyesle.spring_boot_k6.memory;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/memory")
public class MemoryLeakController {

    private static final List<DummyObject> leakList = new ArrayList<>();

    // 요청이 올 때마다 1MB짜리 객체를 리스트에 추가
    @GetMapping("/leak")
    public String makeLeak() {
        leakList.add(new DummyObject());
        return "Total items: " + leakList.size();
    }

    // 메모리를 비움
    @GetMapping("/reset")
    public String resetMemory() {
        leakList.clear();
        return "Memory cleared!";
    }
}
