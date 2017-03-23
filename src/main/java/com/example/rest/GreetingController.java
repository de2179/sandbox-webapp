package com.example.rest;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.rest.domain.Greeting;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s! Welcome to the secured area...";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World User") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }
}