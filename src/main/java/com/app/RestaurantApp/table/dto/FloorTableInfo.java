package com.app.RestaurantApp.table.dto;

public class FloorTableInfo {
    private int numberOfFloors;
    private int maxNumberOfTables; //potencijalno napraviti mapu, da spratovi imaju odredjeni broj stolova

    public FloorTableInfo(){}

    public FloorTableInfo(int numberOfFloors, int maxNumberOfTables) {
        this.numberOfFloors = numberOfFloors;
        this.maxNumberOfTables = maxNumberOfTables;
    }

    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public void setNumberOfFloors(int numberOfFloors) {
        this.numberOfFloors = numberOfFloors;
    }

    public int getMaxNumberOfTables() {
        return maxNumberOfTables;
    }

    public void setMaxNumberOfTables(int maxNumberOfTables) {
        this.maxNumberOfTables = maxNumberOfTables;
    }
}
