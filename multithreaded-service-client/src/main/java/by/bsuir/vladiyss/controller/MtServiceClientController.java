package by.bsuir.vladiyss.controller;

import by.bsuir.vladiyss.service.MtServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class MtServiceClientController {

    private final MtServiceClient mtServiceClient;

    @GetMapping("/start")
    public void start() {
        this.mtServiceClient.start();
    }
}
