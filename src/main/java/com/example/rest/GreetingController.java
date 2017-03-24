package com.example.rest;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.rest.domain.Greeting;

@RestController
public class GreetingController {

    private static final String publicTemplate = "Hello, %s! Welcome to the public area...";
    private static final String secureTemplate = "Hello, %s! Welcome to the secured area...";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/public")
    public Greeting publicGreeting(@RequestParam(value="name", defaultValue="World User") String name) {
    	return new Greeting(counter.incrementAndGet(),
                String.format(secureTemplate, name));
    }
    
    
    @RequestMapping("/secure")
    public Greeting secureGreeting(@RequestParam(value="name", defaultValue="World User") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(secureTemplate, name));
    }
}