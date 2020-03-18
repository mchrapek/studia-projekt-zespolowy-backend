package com.journeyplanner.user.infrastructure.rest;

import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("info")
@AllArgsConstructor
public class InfoController {

    private Environment env;

    @GetMapping
    @CrossOrigin(origins = "*")
    public String info(@RequestHeader("x-username") String username) {
        return "Hello from User Service running at port: " + env.getProperty("local.server.port") + " with auth username: " + username;
    }
}
