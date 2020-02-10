package com.example.sandeep.allinone.Models;

public class HistoryModel {

    private String Date;
    private String time;
    private String timeLimit;



    public HistoryModel() {

    }


    public HistoryModel(String date, String time, String timeLimit) {
        Date = date;
        this.time = time;
        this.timeLimit = timeLimit;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }
}
