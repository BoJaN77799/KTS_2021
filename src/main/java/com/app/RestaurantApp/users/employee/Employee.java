package com.app.RestaurantApp.users.employee;

import com.app.RestaurantApp.bonus.Bonus;
import com.app.RestaurantApp.order.Order;
import com.app.RestaurantApp.salary.Salary;
import com.app.RestaurantApp.users.appUser.AppUser;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Employee extends AppUser {

    @Column(name = "salary")
    private double salary;

    @OneToMany(mappedBy="employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Salary> salaries;

    @OneToMany(mappedBy="employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Bonus> bonuses;

    public Employee() { }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
