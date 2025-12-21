package com.sunyesle.hello;

import com.sunyesle.hello.user.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping("/api")
public class HelloController {
    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping("/{userId}")
    ResponseEntity<String> hello(Locale locale, @PathVariable long userId) {
        try {
            String hello = helloService.sayHello(locale, userId);
            return ResponseEntity.ok(hello);
        } catch (UserNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
