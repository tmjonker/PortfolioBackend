package com.tmjonker.portfoliobackend.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String getIndex() {

        return "ng-project/index.html";
    }
}
