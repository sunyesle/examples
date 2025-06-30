package com.sunyesle.spring_boot_sse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HtmlController {

    @GetMapping("/client")
    public String client() {
        return "client";
    }

    @GetMapping("/sender")
    public String sender() {
        return "sender";
    }
}
