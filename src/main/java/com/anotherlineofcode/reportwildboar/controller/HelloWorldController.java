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

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.stream.Collectors;

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

    @GetMapping("/map")
    public String showMap(Model model){
        return "map";
    }

    @GetMapping("/file/boars_clean")
    public void getBoarsCleanCsv(HttpServletResponse response){
        response.setContentType("text/plain; charset=utf-8");
        try {
            response.getWriter().print(getBoarsCSVAsString());
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    String getBoarsCSVAsString(){
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data/boars_clean.csv");
        if (inputStream != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        } else {
            throw new RuntimeException("resource not found");
        }
    }
}