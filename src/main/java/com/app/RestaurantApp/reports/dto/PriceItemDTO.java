package com.app.RestaurantApp.reports.dto;

public class PriceItemDTO {

    private double priceCount;
    private int itemCount;

    public PriceItemDTO() {}

    public PriceItemDTO(double priceCount, int itemCount){
        this.priceCount = priceCount;
        this.itemCount = itemCount;
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
