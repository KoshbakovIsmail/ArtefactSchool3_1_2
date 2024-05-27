package ru.hogwarts.school32.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {
    @Value("${server.port80}")
    private String serverPort80;

    @GetMapping("/port80")
    public String gertPort() {
        return serverPort80;
    }

}