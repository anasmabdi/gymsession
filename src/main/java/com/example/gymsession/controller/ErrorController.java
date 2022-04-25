package com.example.gymsession.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class ErrorController {

    @GetMapping("/error")
    public String error(){

        return "error";
    }
}
