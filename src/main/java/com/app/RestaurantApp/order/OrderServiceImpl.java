package com.app.RestaurantApp.order;

import com.app.RestaurantApp.drinks.Drink;
import com.app.RestaurantApp.enums.FoodType;
import com.app.RestaurantApp.enums.OrderItemStatus;
import com.app.RestaurantApp.enums.OrderStatus;
import com.app.RestaurantApp.enums.UserType;
import com.app.RestaurantApp.food.Food;
import com.app.RestaurantApp.item.Item;
import com.app.RestaurantApp.item.ItemService;
import com.app.RestaurantApp.notifications.OrderNotification;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
        OrderUtils.checkOrderItemsNumber(order);
        OrderUtils.checkOrderItemsQuantity(order);
        OrderUtils.checkOrderItemsPriority(order);

        order.setWaiter(employeeService.findById(orderDTO.getWaiterId()));
        order.setCreatedAt(Instant.now().toEpochMilli());
        order.setStatus(OrderStatus.NEW);
        order.setNote(orderDTO.getNote());
        order.setTable(tableService.findById(orderDTO.getTableId()));
        OrderUtils.checkBasicOrderInfo(order);
        if(orderRepository.findActiveOrderByTable(order.getTable().getId()).size() > 0)
            throw new OrderException("Table in use!");

        Order savedOrder = orderRepository.save(order);
        List<OrderNotification> orderNotifications = orderNotificationService.notifyNewOrder(order);
        orderNotificationService.saveAll(orderNotifications);

        order.setId(savedOrder.getId());
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
    public Page<Order> findAllNewWithFood(Pageable pageable) {
        return orderRepository.findAllNewWithFood(pageable);
    }

    @Override
    public Page<Order> findAllMyWithFood(Long id, Pageable pageable) {
        return orderRepository.findAllMyWithFood(id, pageable);
    }

    @Override
    public Order findOneWithDrinks(Long id) {
        return orderRepository.findOneWithDrinks(id);
    }

    @Override
    public Page<Order> findAllNewWithDrinks(Pageable pageable) {
        return orderRepository.findAllNewWithDrinks(pageable);
    }

    @Override
    public Page<Order> findAllMyWithDrinks(Long id, Pageable pageable) {
        return orderRepository.findAllMyWithDrinks(id, pageable);
    }

    @Override
    public void acceptOrder(Long id, String email) throws OrderException, UserException {
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

    @Override
    public Order findOneWithOrderItemsForUpdate(Long id) {
        Order order = orderRepository.findOneWithOrderItems(id);
        if(order == null) return null;

        order.getOrderItems().removeIf(orderItem -> orderItem.getStatus() != OrderItemStatus.ORDERED);

        return order;
    }

    @Override
    public Order updateOrder(OrderDTO orderDTO) throws OrderException {
        Order order = findOneWithOrderItemsForUpdate(orderDTO.getId());
        if(order == null) throw new OrderException("Invalid order id sent from front!");
        List<OrderItemOrderCreationDTO> orderItemsDTO = orderDTO.getOrderItems();
        OrderUtils.checkOrderItemsDTONumber(orderItemsDTO);

        OrderUtils.checkNoteLength(orderDTO.getNote());
        order.setNote(orderDTO.getNote());

        List<OrderItem> orderItemsToDelete = new ArrayList<>();
        List<OrderNotification> notificationsToSend = new ArrayList<>(); // Lista koja cuva

        // Izmena postojecih orderItem-a
        for (Iterator<OrderItemOrderCreationDTO> it = orderItemsDTO.iterator(); it.hasNext();){
            OrderItemOrderCreationDTO orderItemDTO = it.next();
            if(orderItemDTO.getId() != null){

                OrderItem orderItemForUpdate = getOrderItemFromOrder(order, orderItemDTO.getId());
                if(orderItemForUpdate == null)
                    throw new OrderException("Order item with id: " + orderItemDTO.getId() + " does not exist in order or cannot be changed!");

                if(orderItemDTO.getQuantity() == 0){ // Ako je quantity na 0 obrisi ga
                    OrderNotification on = orderNotificationService.notifyOrderItemDeleted(order, orderItemForUpdate);
                    if(on != null) notificationsToSend.add(on);

                    order.getOrderItems().remove(orderItemForUpdate);
                    orderItemsToDelete.add(orderItemForUpdate);
                    it.remove();
                    continue;
                }

                boolean quantityChanged = orderItemForUpdate.getQuantity() != orderItemDTO.getQuantity();
                boolean priorityChanged = false;
                if(orderItemForUpdate.getItem() instanceof Food)                                                    // Ako je pice ne gleda se prioritet
                    priorityChanged = orderItemForUpdate.getPriority() != orderItemDTO.getPriority();

                if(priorityChanged || quantityChanged) {
                    if(orderItemForUpdate.getItem() instanceof Drink)                                               // Ako je pice ne gleda se prioriter
                        orderItemForUpdate.setPriority(0);
                    OrderNotification on = orderNotificationService.notifyOrderItemChange(order, orderItemForUpdate, orderItemDTO.getQuantity(), orderItemDTO.getPriority());
                    if(on != null) notificationsToSend.add(on);
                    orderItemForUpdate.setQuantity(orderItemDTO.getQuantity());
                    if(orderItemDTO.getPriority() != -1 && orderItemForUpdate.getItem() instanceof Food)            // Ako je neki broj i hrana podesi mu na taj broj
                        orderItemForUpdate.setPriority(orderItemDTO.getPriority());
                    else if(orderItemDTO.getPriority() == -1 && orderItemForUpdate.getItem() instanceof Food)
                        orderItemForUpdate.setPriority(((Food) orderItemForUpdate.getItem()).getType().ordinal());  // Ako je -1 i hrana podesi mu na default
                }
                it.remove();
            }
        }

        Set<OrderItem> newOrderItems = createNewOrderItems(order, orderItemsDTO);
        Order finalOrder = order;
        newOrderItems.forEach((item) -> finalOrder.getOrderItems().add(item));

        OrderUtils.checkOrderItemsQuantity(finalOrder);
        OrderUtils.checkOrderItemsPriority(finalOrder);

        List<OrderNotification> ons = orderNotificationService.notifyOrderItemAdded(order, newOrderItems);
        notificationsToSend.addAll(ons);

        Order savedOrder = orderRepository.save(finalOrder);    // Cuvanje order-a (svih order item-a)
        orderItemService.deleteAll(orderItemsToDelete);         // Brisanje svih koji koji imaju kvantitet nula
        orderNotificationService.saveAll(notificationsToSend);  // Cuvanje (slanje) svih notifikacija

        order.setId(savedOrder.getId());
        return order;
    }

    private OrderItem getOrderItemFromOrder(Order order, Long id){
        for(OrderItem orderItem : order.getOrderItems())
            if(orderItem.getId().equals(id))
                return orderItem;
        return null;
    }

    private Set<OrderItem> createNewOrderItems(Order order, List<OrderItemOrderCreationDTO> orderItemsDTO) throws OrderException {
        Set<OrderItem> orderItems = new HashSet<>();

        List<Long> itemsId = new ArrayList<>();
        orderItemsDTO.forEach((itemDTO) -> itemsId.add(itemDTO.getItemId()));

        List<Item> items = itemService.findAllWithIds(itemsId);

        if(items == null)
            items = new ArrayList<>();

        if(items.size() != orderItemsDTO.size())
            throw new OrderException("One of items selected for order does not exist in database!");

        orderItemsDTO.sort((item1, item2) -> (int) (item1.getItemId() - item2.getItemId()));
        items.sort((item1, item2) -> (int) (item1.getId() - item2.getId()));

        for(int i = 0; i < items.size(); ++i){
            OrderItem orderItem = new OrderItem();

            orderItem.setItem(items.get(i));
            orderItem.setQuantity(orderItemsDTO.get(i).getQuantity());
            orderItem.setPrice(items.get(i).getCurrentPrice());
            orderItem.setStatus(OrderItemStatus.ORDERED);
            orderItem.setOrder(order);
            orderItems.add(orderItem);

            Integer tempPriority = orderItemsDTO.get(i).getPriority();
            orderItem.setPriority((tempPriority == null) ? -1 : tempPriority);
            if(orderItem.getPriority() == -1 && orderItem.getItem() instanceof Food) {  // Podesavanje prioriteta ukoliko je default (-1)
                FoodType type = ( (Food) orderItem.getItem() ).getType();
                orderItem.setPriority(type.ordinal());
            }
            else if (orderItem.getItem() instanceof Drink) orderItem.setPriority(0);    // Pica nemaju veze sa hranom i prioritetima
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
            if(oi.getStatus() != OrderItemStatus.ORDERED)
                profit += (oi.getPrice() - oi.getItem().getCost()) * oi.getQuantity();
        order.setProfit(profit);

        orderNotificationService.deleteOrderNotifications(order);
        orderRepository.save(order);
        return order;
    }

    @Override
    public List<Order> findAllOrderInIntervalOfDates(Long dateFrom, Long dateTo) {
        return orderRepository.findAllOrderInIntervalOfDates(dateFrom, dateTo);
    }

    @Override
    public Page<Order> searchOrders(String searchField, String orderStatus, Pageable pageable) {
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
