package com.app.RestaurantApp.notifications;

import com.app.RestaurantApp.order.Order;
import com.app.RestaurantApp.users.employee.Employee;

import javax.persistence.*;

@Entity
public class OrderNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="seen", nullable = false)
    private boolean seen;

    @Column(name="message", nullable = false)
    private String message;

    @ManyToOne
    private Order order;

    @ManyToOne
    private Employee employee;

    public OrderNotification() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
