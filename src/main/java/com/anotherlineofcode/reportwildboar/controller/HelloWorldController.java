package com.anotherlineofcode.reportwildboar.controller;

import com.anotherlineofcode.reportwildboar.model.Prediction;
import com.anotherlineofcode.reportwildboar.model.Report;
import com.anotherlineofcode.reportwildboar.repository.PredictionRepository;
import com.anotherlineofcode.reportwildboar.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class HelloWorldController {

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    PredictionRepository predictionRepository;

    @GetMapping({"/"})
    public String helloWorld(Model model) {
        return "index";
    }

    @GetMapping("/report")
    public String reportForm(Model model){
        model.addAttribute("report", new Report());
        return "boar-report";
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

    @GetMapping("/photo")
    public String takePhoto(Model model){
        return "photo";
    }

    @PostMapping("/photo")
    public void uploadFile(@RequestParam MultipartFile file, HttpServletRequest request, HttpServletResponse response){
        File persistFile = new File("dummyPhoto.jpg");
        try {
            file.transferTo(persistFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/predictions")
    public void getPredictionsCSV(HttpServletResponse response) {
        response.setContentType("text/plain; charset=utf-8");
        try {
            response.getWriter().print(getAllPredictions());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getAllPredictions() {
        List<Prediction> predictionIterable = predictionRepository.findAll();
        return StreamSupport.stream(predictionIterable.spliterator(), false)
                .map(prediction -> prediction.getDate().toString() + "," + prediction.getSquare_id().toString() + "," + prediction.getPrediction().toString())
                .collect(Collectors.joining(System.lineSeparator()));
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
        List<Report> repositoryList = reportRepository.findAll();
        String reports =  StreamSupport.stream(repositoryList.spliterator(), false)
                .filter(report -> report.getSource().equals(4))
                .map(repository -> repository.getGeoLat().toString()
                        + "," + repository.getGeoLong().toString()
                        + "," + repository.getTimestamp().toString())
                .collect(Collectors.joining(System.lineSeparator()));
        return reports;
    }
}