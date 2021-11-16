package com.app.RestaurantApp.item.dto;

import com.app.RestaurantApp.price.Price;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ItemPriceDTO {

    private Long id;
    private double newPrice;
    private String dateFrom;
    private String dateTo;

    public ItemPriceDTO() {

    }

    public ItemPriceDTO(Price p) {
        this.newPrice = p.getAmount();
        LocalDate dateFrom =
                Instant.ofEpochMilli(p.getDateFrom()).atZone(ZoneId.systemDefault()).toLocalDate();
        this.dateFrom = dateFrom.format(DateTimeFormatter.ofPattern("dd.MM.yyyy."));
        this.dateTo = "nije odredjen";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(double newPrice) {
        this.newPrice = newPrice;
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
