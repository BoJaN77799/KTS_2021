package com.app.RestaurantApp.bonus;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class BonusDTO {

    private double amount;
    private String date;

    public BonusDTO() {}

    public BonusDTO(Bonus b){
        this.amount = b.getAmount();
        LocalDate date =
                Instant.ofEpochMilli(b.getDate()).atZone(ZoneId.systemDefault()).toLocalDate();
        this.date = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy."));
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
