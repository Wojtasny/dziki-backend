package com.anotherlineofcode.reportwildboar.model;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private Integer source = 1;
    private Long timestamp;
    private Integer quantity;
    private Float geoLat;
    private Float geoLong;
    private String geoLatString;
    private String geoLongString;


    public String getGeoLatString() {
        return geoLatString;
    }

    public void setGeoLatString(String geoLatString) {
        this.geoLatString = geoLatString;
    }

    public String getGeoLongString() {
        return geoLongString;
    }

    public void setGeoLongString(String geoLongString) {
        this.geoLongString = geoLongString;
    }

    public Float getGeoLat() {
        return geoLat;
    }

    public void setGeoLat(Float geoLat) {
        this.geoLat = geoLat;
    }

    public Float getGeoLong() {
        return geoLong;
    }

    public void setGeoLong(Float geoLong) {
        this.geoLong = geoLong;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
