package com.anotherlineofcode.reportwildboar.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Prediction {

    private Long date;
    @Id
    private Integer square_id;
    private Integer prediction;

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Integer getSquare_id() {
        return square_id;
    }

    public void setSquare_id(Integer square_id) {
        this.square_id = square_id;
    }

    public Integer getPrediction() {
        return prediction;
    }

    public void setPrediction(Integer prediction) {
        this.prediction = prediction;
    }
}
