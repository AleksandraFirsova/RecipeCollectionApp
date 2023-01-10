package pro.sky.recipecollectionapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {

    @GetMapping
    public String start() {
        return "start";
    }

    @GetMapping("/info")
    public String page() {
        return "info";
    }
}
