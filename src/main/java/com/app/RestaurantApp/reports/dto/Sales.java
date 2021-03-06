package com.app.RestaurantApp.reports.dto;

import java.util.HashMap;
import java.util.Map;

public class Sales {

    private Long itemId;
    private String name;
    private double priceCount;
    private int itemCount;
    private Map<String, PriceItemDTO> salesPerMonth;

    public Sales() {
        this.salesPerMonth = new HashMap<>();
    }

    public Sales(Long itemId, String name, double priceCount, int itemCount, String month) {
        this();
        this.itemId = itemId;
        this.name = name;
        this.priceCount = priceCount;
        this.itemCount = itemCount;
        this.salesPerMonth.put(month, new PriceItemDTO(priceCount, itemCount));
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

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Map<String, PriceItemDTO> getSalesPerMonth() {
        return salesPerMonth;
    }

    public void setSalesPerMonth(Map<String, PriceItemDTO> salesPerMonth) {
        this.salesPerMonth = salesPerMonth;
    }
}
