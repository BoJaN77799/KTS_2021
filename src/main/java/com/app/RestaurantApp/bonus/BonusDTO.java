package com.app.RestaurantApp.bonus;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class BonusDTO {

    private double amount;
    private String date;
    private String email;

    public BonusDTO() {}

    public BonusDTO(Bonus b){
        this.amount = b.getAmount();
        LocalDate date =
                Instant.ofEpochMilli(b.getDate()).atZone(ZoneId.systemDefault()).toLocalDate();
        this.date = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy."));
        this.email = b.getEmployee().getEmail();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
