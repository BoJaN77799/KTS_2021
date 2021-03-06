package com.app.RestaurantApp.order;

import com.app.RestaurantApp.enums.OrderStatus;
import com.app.RestaurantApp.orderItem.OrderItem;
import com.app.RestaurantApp.table.Table;
import com.app.RestaurantApp.users.employee.Employee;

import javax.persistence.*;
import java.util.Set;

@Entity
@javax.persistence.Table(name="restaurant_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "note")
    private String note;

    @ManyToOne
    private Table table;

    @ManyToOne
    private Employee waiter;

    @ManyToOne
    private Employee barman;

    @ManyToOne
    private Employee cook;

    @OneToMany(mappedBy="order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<OrderItem> orderItems;

    @Column(name = "profit")
    private Double profit;

    public Order() {}

    public Order(Long id, OrderStatus status, Long createdAt, Employee waiter, Employee barman, Employee cook) {
        this.id = id;
        this.status = status;
        this.createdAt = createdAt;
        this.waiter = waiter;
        this.barman = barman;
        this.cook = cook;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Employee getWaiter() {
        return waiter;
    }

    public void setWaiter(Employee waiter) {
        this.waiter = waiter;
    }

    public Employee getBarman() {
        return barman;
    }

    public void setBarman(Employee barmen) {
        this.barman = barmen;
    }

    public Employee getCook() {
        return cook;
    }

    public void setCook(Employee cook) {
        this.cook = cook;
    }

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }
}
