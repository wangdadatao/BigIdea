package com.datao.bigidea.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class HomeController {

    @RequestMapping("")
    public String index() {
        return "build/index.jsp";
    }


}
