package net.engineeringdigest.journalApp.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // a special type of component help to state endpoints
@Tag(name = "HealthCheck APIs" )
public class HealthCheck {

    @GetMapping("/health-check")
    public String healthCheck(){
        return "System is working";
    }


}