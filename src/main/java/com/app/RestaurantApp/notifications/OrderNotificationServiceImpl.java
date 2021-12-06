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
    public List<OrderNotification> notifyNewOrder(Order order) {
        List<Employee> employees = employeeService.findAll();
        List<OrderNotification> orderNotifications = new ArrayList<>();

        for(Employee employee : employees){
            UserType type = employee.getUserType();
            boolean orderHasFood = orderHasFood(order);
            if((type == UserType.COOK && orderHasFood) || (type == UserType.HEAD_COOK && orderHasFood) || (type == UserType.BARMAN && orderHasDrink(order))){
                OrderNotification orderNotification = new OrderNotification();
                orderNotification.setOrder(order);
                orderNotification.setEmployee(employee);
                orderNotification.setSeen(false);
                String msg = "New order from table " + order.getTable().getId() + ".";
                orderNotification.setMessage(msg);

                orderNotifications.add(orderNotification);
            }
        }

        return orderNotifications;
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
    public OrderNotification notifyOrderItemChange(Order order, OrderItem oldOrderItem, int newQuantity, int newPriority) {
        boolean quantityChanged;
        boolean priorityChanged;

        quantityChanged = oldOrderItem.getQuantity() != newQuantity;
        priorityChanged = oldOrderItem.getPriority() != newPriority;

        if(!priorityChanged && !quantityChanged) return null; // Ako nije promenjen ni prioritet ni kvantitet vrati se

        Employee employeeToNotify = getRightEmployee(order, oldOrderItem);
        if(employeeToNotify == null) return null;             // Ako je radnik null vrati se

        OrderNotification orderNotification = createBlankOrderNotification(order);
        orderNotification.setEmployee(employeeToNotify);
        orderNotification.setMessage(generateMsg(order, oldOrderItem, quantityChanged, priorityChanged, newQuantity, newPriority));

        return orderNotification;
    }

    private String generateMsg(Order order, OrderItem orderItem, boolean quantityChanged, boolean priorityChanged,
                               int newQuantity, int newPriority){

        StringBuilder msg = new StringBuilder();

        msg.append(orderItem.getItem().getName() + " from order #" + order.getId() + ", on table number " +
                order.getTable().getId() + ":");

        if(quantityChanged)
            msg.append("\n-Quantity changed from " + orderItem.getQuantity() + " to " + newQuantity + ".");
        if(priorityChanged)
            msg.append("\n-Priority changed from " + ((orderItem.getPriority() == -1) ? "default" : orderItem.getPriority()) +
                    " to " + ((newPriority == -1) ? "default" : newPriority) + ".");

        return msg.toString();
    }

    @Override
    public List<OrderNotification> notifyOrderItemAdded(Order order, Set<OrderItem> orderItems) {
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

        return orderNotifications;

    }

    @Override
    public OrderNotification notifyOrderItemDeleted(Order order, OrderItem orderItem) {
        Employee employeeToNotify = getRightEmployee(order, orderItem);
        if(employeeToNotify == null) return null; // Ako je radnik null vrati se

        OrderNotification orderNotification = createBlankOrderNotification(order);
        orderNotification.setEmployee(employeeToNotify);
        String msg = orderItem.getItem().getName() + " has been deleted from order #" + order.getId() +
                ", on table number " + order.getTable().getId();
        orderNotification.setMessage(msg);

        return orderNotification;
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

    @Override
    public OrderNotification notifyWaiterOrderItemStatusFinished(OrderItem orderItem) {
        Order order = orderItem.getOrder();

        OrderNotification orderNotification = createBlankOrderNotification(order);
        orderNotification.setEmployee(order.getWaiter());

        String msg = orderItem.getItem().getName() + " from order #" + order.getId() +
                " is finished and ready to deliver to table " + order.getTable().getId() + ".";
        orderNotification.setMessage(msg);

        return orderNotification;
    }

    @Override
    public void saveAll(List<OrderNotification> orderNotifications) {
        orderNotificationRepository.saveAll(orderNotifications);
    }


}
