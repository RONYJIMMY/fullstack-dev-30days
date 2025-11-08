package com.dream.greetingservice.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class GreetingController {
    @GetMapping("/greet")
        public String greet(@RequestParam String name){
//            return "Hello, " + name + "!";  //plain text response
            return "{ \"message\": \"Hello, " + name + "!\" }"; // JSON response
    }
}
