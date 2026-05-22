package com.interns.management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/page_d_acceil")
    public String welcome() {
        return "page_d_acceil";
    }
}
