package ru.hogwarts.school32.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController2 {

    @Value("${server.port90}")
    private String serverPort90;

    @GetMapping("/port90")
    public String getPort() {
        return serverPort90;
    }
}
