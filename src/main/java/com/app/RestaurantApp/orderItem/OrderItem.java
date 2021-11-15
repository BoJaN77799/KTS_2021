package com.app.RestaurantApp.orderItem;

import com.app.RestaurantApp.enums.OrderItemStatus;
import com.app.RestaurantApp.item.Item;
import com.app.RestaurantApp.order.Order;
import com.app.RestaurantApp.users.employee.Employee;

import javax.persistence.*;

@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderItemStatus status;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "priority", nullable = false)
    private boolean priority;

    @ManyToOne
    @JoinColumn(name="order_id", nullable=false)
    private Order order;

    @ManyToOne
    @JoinColumn(name="item_id", nullable=false)
    private Item item;

    public OrderItem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public OrderItemStatus getStatus() {
        return status;
    }

    public void setStatus(OrderItemStatus status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isPriority() {
        return priority;
    }

    public void setPriority(boolean priority) {
        this.priority = priority;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
