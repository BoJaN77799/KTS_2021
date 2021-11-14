package com.app.RestaurantApp.order;

import com.app.RestaurantApp.enums.OrderItemStatus;
import com.app.RestaurantApp.enums.OrderStatus;
import com.app.RestaurantApp.item.Item;
import com.app.RestaurantApp.item.ItemRepository;
import com.app.RestaurantApp.order.dto.OrderDTO;
import com.app.RestaurantApp.orderItem.OrderItem;
import com.app.RestaurantApp.orderItem.OrderItemRepository;
import com.app.RestaurantApp.orderItem.dto.OrderItemSimpleDTO;
import com.app.RestaurantApp.table.TableRepository;
import com.app.RestaurantApp.users.employee.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Order createOrder(OrderDTO orderDTO) {
        List<OrderItemSimpleDTO> orderItemSimpleDTOs = orderDTO.getOrderItems();
        List<Long> itemsId = new ArrayList<>();
        orderItemSimpleDTOs.forEach((itemDTO) -> itemsId.add(itemDTO.getItemId()));

        List<Item> items = itemRepository.findAllWithIds(itemsId);

        Collections.sort(orderItemSimpleDTOs,(item1, item2) -> (int) (item1.getItemId() - item2.getItemId()));
        Collections.sort(items,(item1, item2) -> (int) (item1.getId() - item2.getId()));

        Set<OrderItem> orderItems = new HashSet<OrderItem>();
        Order order = new Order();

        for(int i = 0; i < items.size(); ++i){
            OrderItem orderItem = new OrderItem();

            orderItem.setPriority(orderItemSimpleDTOs.get(i).getPriority());
            orderItem.setQuantity(orderItemSimpleDTOs.get(i).getQuantity());
            orderItem.setPrice(items.get(i).getCurrentPrice());
            orderItem.setStatus(OrderItemStatus.ORDERED);
            orderItem.setItem(items.get(i));
            orderItem.setOrder(order);

            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);
        order.setWaiter(employeeRepository.findById(orderDTO.getWaiterId()).orElse(null));
        order.setCreatedAt(Instant.now().toEpochMilli());
        order.setStatus(OrderStatus.NEW);
        order.setNote(orderDTO.getNote());
        order.setTable(tableRepository.findById(orderDTO.getTableId()).orElse(null));
        order.getTable().setActive(true);
        tableRepository.save(order.getTable());

        orderRepository.save(order);
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


}
