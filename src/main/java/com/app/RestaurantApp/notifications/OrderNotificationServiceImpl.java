package com.app.RestaurantApp.notifications;

import com.app.RestaurantApp.drinks.Drink;
import com.app.RestaurantApp.enums.UserType;
import com.app.RestaurantApp.food.Food;
import com.app.RestaurantApp.order.Order;
import com.app.RestaurantApp.orderItem.OrderItem;
import com.app.RestaurantApp.users.employee.Employee;
import com.app.RestaurantApp.users.employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class OrderNotificationServiceImpl implements OrderNotificationService {

    @Autowired
    OrderNotificationRepository orderNotificationRepository;

    @Autowired
    EmployeeService employeeService;

    @Override
    public void notifyNewOrder(Order order) {
        List<Employee> employees = employeeService.findAll();
        List<OrderNotification> orderNotifications = new ArrayList<>();

        for(Employee employee : employees){
            UserType type = employee.getUserType();

            if(type == UserType.COOK && orderHasFood(order) || type == UserType.BARMAN && orderHasDrink(order)){
                OrderNotification orderNotification = new OrderNotification();
                orderNotification.setOrder(order);
                orderNotification.setEmployee(employee);
                orderNotification.setSeen(false);
                String msg = "New order from table " + order.getTable().getId() + ".";
                orderNotification.setMessage(msg);

                orderNotifications.add(orderNotification);
            }
        }

        orderNotificationRepository.saveAll(orderNotifications);
    }

    private boolean orderHasFood(Order order){
        for(OrderItem orderItem : order.getOrderItems()){
            if(orderItem.getItem() instanceof Food) {
                return true;
            }
        }
        return false;
    }

    private boolean orderHasDrink(Order order){
        for(OrderItem orderItem : order.getOrderItems()){
            if(orderItem.getItem() instanceof Drink) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void notifyOrderItemChange(Order order, OrderItem oldOrderItem, int newQuantity, boolean newPriority) {
        boolean quantityChanged;
        boolean priorityChanged;

        quantityChanged = oldOrderItem.getQuantity() != newQuantity;
        priorityChanged = oldOrderItem.isPriority() != newPriority;

        if(!priorityChanged && !quantityChanged) return; // Ako nije promenjen ni prioritet ni kvantitet vrati se

        Employee employeeToNotify = getRightEmployee(order, oldOrderItem);
        if(employeeToNotify == null) return;             // Ako je radnik null vrati se

        OrderNotification orderNotification = createBlankOrderNotification(order);
        orderNotification.setEmployee(employeeToNotify);
        orderNotification.setMessage(generateMsg(order, oldOrderItem, quantityChanged, priorityChanged, newQuantity, newPriority));

        orderNotificationRepository.save(orderNotification);
    }

    private String generateMsg(Order order, OrderItem orderItem, boolean quantityChanged, boolean priorityChanged,
                               int newQuantity, boolean newPriority){

        StringBuilder msg = new StringBuilder();

        msg.append(orderItem.getItem().getName() + " from order #" + order.getId() + ", on table number " +
                order.getTable().getId() + ":");

        if(quantityChanged)
            msg.append("\n-Quantity changed from " + orderItem.getQuantity() + " to " + newQuantity + ".");
        if(priorityChanged && newPriority)
            msg.append("\n-Priority changed to HIGH.");
        if(priorityChanged && !newPriority)
            msg.append("\n-Priority changed to LOW.");

        return msg.toString();
    }

    @Override
    public void notifyOrderItemAdded(Order order, Set<OrderItem> orderItems) {
        List<OrderNotification> orderNotifications = new ArrayList<>();
        for(OrderItem orderItem : orderItems){
            Employee employeeToNotify = getRightEmployee(order, orderItem);
            if(employeeToNotify == null) continue; // Ako je radnik null preskoci

            OrderNotification orderNotification = createBlankOrderNotification(order);
            orderNotification.setEmployee(employeeToNotify);
            String msg = orderItem.getItem().getName() + "(x" + orderItem.getQuantity() + ") has been added to order #" + order.getId() +
                    ", on table number " + order.getTable().getId();
            orderNotification.setMessage(msg);

            orderNotifications.add(orderNotification);
        }
        if(orderNotifications.size() > 0)
            orderNotificationRepository.saveAll(orderNotifications);
    }

    @Override
    public void notifyOrderItemDeleted(Order order, OrderItem orderItem) {
        Employee employeeToNotify = getRightEmployee(order, orderItem);
        if(employeeToNotify == null) return; // Ako je radnik null vrati se

        OrderNotification orderNotification = createBlankOrderNotification(order);
        orderNotification.setEmployee(employeeToNotify);
        String msg = orderItem.getItem().getName() + " has been deleted from order #" + order.getId() +
                ", on table number " + order.getTable().getId();
        orderNotification.setMessage(msg);

        orderNotificationRepository.save(orderNotification);
    }

    private OrderNotification createBlankOrderNotification(Order order){
        /*
            Creates blank order notification that can be used in all three types of notifications for update
        */
        OrderNotification orderNotification = new OrderNotification();
        orderNotification.setOrder(order);
        orderNotification.setSeen(false);

        return orderNotification;
    }

    private Employee getRightEmployee(Order order, OrderItem orderItem){
        Employee cook = order.getCook();
        Employee barmen = order.getBarman();

        if(cook != null && orderItem.getItem() instanceof Food)
            return cook;
        else if(barmen != null && orderItem.getItem() instanceof Drink)
            return barmen;

        return null;
    }

    @Override
    public void deleteOrderNotifications(Order order) {
        orderNotificationRepository.deleteAllByOrder(order);
    }


}
