package org.mql.laktam.speedreadbackend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("public")
public class PublicController {
    
    @GetMapping("/test")
    public String testEndpoint(){
        return "test successful";
    }
}
