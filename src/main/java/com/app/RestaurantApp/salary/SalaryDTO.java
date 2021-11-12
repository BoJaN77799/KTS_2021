package com.app.RestaurantApp.salary;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class SalaryDTO {

    private double amount;
    private String dateFrom;
    private String dateTo;

    public SalaryDTO() {}

    public SalaryDTO(Salary salary){
        this.amount = salary.getAmount();
        LocalDate dateTo =
                Instant.ofEpochMilli(salary.getDateTo()).atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dateFrom =
                Instant.ofEpochMilli(salary.getDateFrom()).atZone(ZoneId.systemDefault()).toLocalDate();
        this.dateFrom = dateFrom.format(DateTimeFormatter.ofPattern("dd.MM.yyyy."));
        this.dateTo = dateTo.format(DateTimeFormatter.ofPattern("dd.MM.yyyy."));
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }
}