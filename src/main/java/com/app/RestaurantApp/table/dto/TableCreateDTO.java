package com.app.RestaurantApp.table.dto;

import com.app.RestaurantApp.table.Table;

public class TableCreateDTO {

    private double x;

    private double y;

    private int floor;

    public TableCreateDTO() {

    }

    public TableCreateDTO(double x, double y, int floor) {
        this.x = x;
        this.y = y;
        this.floor = floor;
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

    public Table convertToTable(){
        Table table = new Table();
        table.setX(this.x);
        table.setY(this.y);
        table.setFloor(this.floor);

        return table;
    }
}
