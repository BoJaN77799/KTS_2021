package com.app.RestaurantApp.reports.dto;

public class IncomeExpenses {

    private double income;
    private double expenses;

    public IncomeExpenses() {}

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public double getExpenses() {
        return expenses;
    }

    public void setExpenses(double expenses) {
        this.expenses = expenses;
    }
}
