package com.app.RestaurantApp.salary;


public class SalaryUtils {

    public static void CheckSalaryInfo(Salary salary) throws SalaryException {
        if (salary == null)
            throw new SalaryException("Invalid salary sent from front!");
        else if (salary.getDateFrom() == null || salary.getEmployee() == null)
            throw new SalaryException("Invalid salary data sent from front! Null values in attributes!");

        if (salary.getAmount() <= 0)
            throw new SalaryException("Salary can not be negative number or zero!");
    }
}
