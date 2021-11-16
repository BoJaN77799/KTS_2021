package com.app.RestaurantApp.reports.dto;

public class Sales {

    private String name;
    private double priceCount;
    private int itemCount;

    public Sales() {}

    public Sales(String name, double priceCount, int itemCount) {
        this.name = name;
        this.priceCount = priceCount;
        this.itemCount = itemCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPriceCount() {
        return priceCount;
    }

    public void setPriceCount(double priceCount) {
        this.priceCount = priceCount;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }
}
