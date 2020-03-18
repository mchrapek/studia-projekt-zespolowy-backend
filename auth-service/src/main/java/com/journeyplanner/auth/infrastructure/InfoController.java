package com.journeyplanner.auth.infrastructure;

import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("info")
@AllArgsConstructor
public class InfoController {

    private Environment env;

    @GetMapping
    @CrossOrigin(origins = "*")
    public String info() {
        return "Hello from Auth Service running at port: " + env.getProperty("local.server.port");
    }
}
