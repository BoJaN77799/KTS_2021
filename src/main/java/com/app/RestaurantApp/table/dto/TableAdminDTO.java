package com.app.RestaurantApp.table.dto;

import com.app.RestaurantApp.table.Table;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class TableAdminDTO {

    private Long id;

    private Boolean active;

    private double x;

    private double y;

    private int floor;

    public TableAdminDTO() {
    }

    public TableAdminDTO(Table table){
        this.id = table.getId();
        this.active = table.getActive();
        this.x = table.getX();
        this.y = table.getY();
        this.floor = table.getFloor();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }
}
