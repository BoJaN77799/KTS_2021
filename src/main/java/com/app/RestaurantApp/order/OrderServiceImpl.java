package com.app.RestaurantApp.order;

import com.app.RestaurantApp.enums.OrderItemStatus;
import com.app.RestaurantApp.enums.OrderStatus;
import com.app.RestaurantApp.enums.UserType;
import com.app.RestaurantApp.item.Item;
import com.app.RestaurantApp.item.ItemService;
import com.app.RestaurantApp.notifications.OrderNotificationService;
import com.app.RestaurantApp.order.dto.OrderDTO;
import com.app.RestaurantApp.orderItem.OrderItem;

import com.app.RestaurantApp.users.UserException;
import com.app.RestaurantApp.users.employee.Employee;

import com.app.RestaurantApp.orderItem.OrderItemService;
import com.app.RestaurantApp.orderItem.dto.OrderItemOrderCreationDTO;
import com.app.RestaurantApp.table.TableService;


import com.app.RestaurantApp.users.employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemService itemService;

    @Autowired
    private TableService tableService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private OrderNotificationService orderNotificationService;

    @Override
    public Order createOrder(OrderDTO orderDTO) throws OrderException {
        List<OrderItemOrderCreationDTO> orderItemOrderCreationDTOS = orderDTO.getOrderItems();
        Order order = new Order();

        order.setOrderItems(createNewOrderItems(order, orderItemOrderCreationDTOS));
        order.setWaiter(employeeService.findById(orderDTO.getWaiterId()));
        order.setCreatedAt(Instant.now().toEpochMilli());
        order.setStatus(OrderStatus.NEW);
        order.setNote(orderDTO.getNote());
        order.setTable(tableService.findById(orderDTO.getTableId()));

        OrderUtils.checkBasicOrderInfo(order);

        orderRepository.save(order);
        orderNotificationService.notifyNewOrder(order);

        return order;
    }

    @Override
    public Order findOne(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public Order findOneWithOrderItems(Long id) {
        return orderRepository.findOneWithOrderItems(id);
    }

    @Override
    public Order findOneWithFood(Long id){
        return orderRepository.findOneWithFood(id);
    }

    @Override
    public List<Order> findAllNewWithFood() {
        return orderRepository.findAllNewWithFood();
    }

    @Override
    public List<Order> findAllMyWithFood(Long id) {
        return orderRepository.findAllMyWithFood(id);
    }

    @Override
    public Order findOneWithDrinks(Long id) {
        return orderRepository.findOneWithDrinks(id);
    }

    @Override
    public List<Order> findAllNewWithDrinks() {
        return orderRepository.findAllNewWithDrinks();
    }

    @Override
    public List<Order> findAllMyWithDrinks(Long id) {
        return orderRepository.findAllMyWithDrinks(id);
    }

    @Override
    public void acceptOrder(Long id, String email) throws OrderException, UserException{
        Order order = findOne(id);
        if(order == null) throw new OrderException("Order not found.");

        Employee employee = employeeService.findByEmail(email);
        if(employee == null) throw new UserException("User not found.");

        if(employee.getUserType() == UserType.BARMAN)
            if(order.getBarman() != null)
                throw new OrderException("Barman already accepted.");
            else
                order.setBarman(employee);
        else
            if(order.getCook() != null)
                throw new OrderException("Cook already accepted.");
            else
                order.setCook(employee);
        if(order.getStatus() == OrderStatus.NEW)
            order.setStatus(OrderStatus.IN_PROGRESS);
        orderRepository.save(order);
    }


    public Order findOneWithOrderItemsForUpdate(Long id) {
        return orderRepository.findOneWithOrderItemsForUpdate(id);
    }

    @Override
    public Order updateOrder(OrderDTO orderDTO) throws OrderException {
        Order order = orderRepository.findOneWithOrderItemsForUpdate(orderDTO.getId());
        if(order == null) return order;
        List<OrderItemOrderCreationDTO> orderItemsDTO = orderDTO.getOrderItems();

        OrderUtils.checkNoteLength(orderDTO.getNote());
        order.setNote(orderDTO.getNote());

        List<OrderItem> orderItemsToDelete = new ArrayList<>();
        // Izmena postojecih orderItem-a
        for (Iterator<OrderItemOrderCreationDTO> it = orderItemsDTO.iterator(); it.hasNext();){
            OrderItemOrderCreationDTO orderItemDTO = it.next();
            if(orderItemDTO.getId() != null){

                OrderItem orderItemForUpdate = getOrderItemFromOrder(order, orderItemDTO.getId());
                if(orderItemForUpdate == null)
                    throw new OrderException("Order item with id: " + orderItemDTO.getId() + " does not exist in order or cannot be changed!");

                if(orderItemDTO.getQuantity() == 0){ // Ako je quantity na 0 obrisi ga
                    orderNotificationService.notifyOrderItemDeleted(order, orderItemForUpdate);
                    order.getOrderItems().remove(orderItemForUpdate);
                    orderItemsToDelete.add(orderItemForUpdate);
                    it.remove();
                    continue;
                }

                orderNotificationService.notifyOrderItemChange(order, orderItemForUpdate, orderItemDTO.getQuantity(), orderItemDTO.getPriority());
                orderItemForUpdate.setQuantity(orderItemDTO.getQuantity());
                orderItemForUpdate.setPriority(orderItemDTO.getPriority());
                it.remove();
            }
        }

        Set<OrderItem> newOrderItems = createNewOrderItems(order, orderItemsDTO);
        Order finalOrder = order;
        newOrderItems.forEach((item) -> finalOrder.getOrderItems().add(item));
        orderNotificationService.notifyOrderItemAdded(order, newOrderItems);

        order = orderRepository.save(finalOrder);
        orderItemService.deleteAll(orderItemsToDelete);
        return order;
    }

    private OrderItem getOrderItemFromOrder(Order order, Long id){
        for(OrderItem orderItem : order.getOrderItems())
            if(orderItem.getId().equals(id))
                return orderItem;
        return null;
    }


    private Set<OrderItem> createNewOrderItems(Order order, List<OrderItemOrderCreationDTO> orderItemsDTO) throws OrderException {
        Set<OrderItem> orderItems = new HashSet<OrderItem>();

        List<Long> itemsId = new ArrayList<>();
        orderItemsDTO.forEach((itemDTO) -> itemsId.add(itemDTO.getItemId()));

        List<Item> items = itemService.findAllWithIds(itemsId);

        if(items.size() != orderItemsDTO.size())
            throw new OrderException("One of items selected for order does not exist in database!");

        Collections.sort(orderItemsDTO,(item1, item2) -> (int) (item1.getItemId() - item2.getItemId()));
        Collections.sort(items,(item1, item2) -> (int) (item1.getId() - item2.getId()));

        for(int i = 0; i < items.size(); ++i){
            OrderItem orderItem = new OrderItem();

            orderItem.setPriority(orderItemsDTO.get(i).getPriority());
            orderItem.setQuantity(orderItemsDTO.get(i).getQuantity());
            orderItem.setPrice(items.get(i).getCurrentPrice());
            orderItem.setStatus(OrderItemStatus.ORDERED);
            orderItem.setItem(items.get(i));
            orderItem.setOrder(order);

            orderItems.add(orderItem);
        }

        return orderItems;
    }

    @Override
    public Order finishOrder(Long id){
        Order order = orderRepository.findOneWithOrderItems(id);
        if(order == null) return null;
        order.setStatus(OrderStatus.FINISHED);

        double profit = 0;
        for (OrderItem oi : order.getOrderItems())
            if(oi.getStatus() == OrderItemStatus.IN_PROGRESS || oi.getStatus() == OrderItemStatus.FINISHED || oi.getStatus() == OrderItemStatus.DELIVERED)
                profit += (oi.getPrice() - oi.getItem().getCost()) * oi.getQuantity();
        order.setProfit(profit);

        orderNotificationService.deleteOrderNotifications(order);
        return orderRepository.save(order);
    }

    @Override
    public List<Order> findAllOrderInIntervalOfDates(Long dateFrom, Long dateTo) {
        return orderRepository.findAllOrderInIntervalOfDates(dateFrom, dateTo);
    }

    @Override
    public List<Order> searchOrders(String searchField, String orderStatus, Pageable pageable) {
        if (searchField == null)
            searchField = "";
        if (orderStatus == null)
            orderStatus = "";

        return orderRepository.searchOrders(searchField, orderStatus, pageable);
    }

    @Override
    public Order findOrderAtTable(Long tableID){
        List<Order> orders = orderRepository.findActiveFromTable(tableID);
        //inace mora da bude samo jedno aktivno porucivanje za stolom
        return orders.size() > 0 ? orders.get(0) : null;
    }
  
    @Override
    public List<Order> getOrdersByDate(long dateFrom, long dateTo) {
        return orderRepository.getOrdersByDate(dateFrom, dateTo);
    }

}
