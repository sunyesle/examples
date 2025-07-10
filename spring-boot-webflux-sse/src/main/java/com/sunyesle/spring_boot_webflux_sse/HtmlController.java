package com.sunyesle.spring_boot_webflux_sse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.result.view.Rendering;

@Controller
public class HtmlController {

    @GetMapping("/client")
    public Rendering client() {
        return Rendering.view("client").build();
    }

    @GetMapping("/sender")
    public Rendering sender() {
        return Rendering.view("sender").build();
    }
}
