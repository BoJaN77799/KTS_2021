package com.app.RestaurantApp.table.dto;

public class TableUpdateDTO {
    private Long id;

    private double x;

    private double y;

    public TableUpdateDTO(){}

    public TableUpdateDTO(Long id, double x, double y){
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
