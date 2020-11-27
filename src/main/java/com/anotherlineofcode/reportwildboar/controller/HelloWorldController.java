package com.anotherlineofcode.reportwildboar.controller;

import com.anotherlineofcode.reportwildboar.handlereport.BoarReport;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloWorldController {

    @GetMapping({"/", "hello"})
    public String helloWorld(@RequestParam(required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name + " TELEMABK!");
        return "hello-world";
    }

    @GetMapping("/report")
    public String reportForm(Model model){
        model.addAttribute("boarReport", new BoarReport());
        return "boar-report";
    }

    @PostMapping("/report")
    public String reportBoar(@ModelAttribute BoarReport boarReport, Model model) {
        model.addAttribute("boarReport", boarReport);
        return "report-result";
    }
}