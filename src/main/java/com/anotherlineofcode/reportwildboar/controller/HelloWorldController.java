package com.anotherlineofcode.reportwildboar.controller;

import com.anotherlineofcode.reportwildboar.model.Report;
import com.anotherlineofcode.reportwildboar.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
public class HelloWorldController {

    @Autowired
    ReportRepository reportRepository;

    @GetMapping({"/", "hello"})
    public String helloWorld(@RequestParam(required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name + " TELEMABK!");
        return "hello-world";
    }

    @GetMapping("/report")
    public String reportForm(Model model){
        model.addAttribute("report", new Report());
        return "boar-report";
    }

    @PostMapping("/report")
    public String reportBoar(@ModelAttribute Report report, Model model) {
        model.addAttribute("report", report);
        report.setGeoLat(Float.parseFloat(report.getGeoLatString()));
        report.setGeoLong(Float.parseFloat(report.getGeoLongString()));
        Date date = new Date();
        report.setTimestamp(date.getTime());
        reportRepository.save(report);
        return "report-result";
    }
}