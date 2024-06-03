package ru.hogwarts.school32.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school32.service.InfoService;

@RestController
@Tag(name = "InfoController")
public class InfoController {
    private final InfoService infoService;

    public InfoController(InfoService infoService) {
        this.infoService = infoService;
    }

    @GetMapping("/port")
    public String getPort() {
        return infoService.getPort();
    }

    @GetMapping("/calculate-stream")
    public void calculate(@RequestParam Integer limit) {
        infoService.calculate(limit);
    }
}
