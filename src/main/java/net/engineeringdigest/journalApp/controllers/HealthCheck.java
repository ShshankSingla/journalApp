package net.engineeringdigest.journalApp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // a special type of component help to state endpoints
public class HealthCheck {

    @GetMapping("/health-check")
    public String healthCheck(){
        return "System is working";
    }


}