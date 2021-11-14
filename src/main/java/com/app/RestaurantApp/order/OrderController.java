package com.app.RestaurantApp.order;

import com.app.RestaurantApp.order.dto.OrderDTO;
import com.app.RestaurantApp.users.dto.AppUserAdminUserListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderDTO> findOneWithOrderItems(@PathVariable Long id) {
        Order order = orderService.findOneWithOrderItems(id);

        if(order == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(new OrderDTO(order), HttpStatus.OK);
    }

    @GetMapping(value = "/forCook/{id}")
    public ResponseEntity<OrderDTO> findOneWithFood(@PathVariable Long id) {
        Order order = orderService.findOneWithFood(id);

        if(order == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(new OrderDTO(order), HttpStatus.OK);
    }

    @GetMapping(value = "/forCook/all")
    public List<OrderDTO> findAllNewWithFood() {
        List<Order> orders = orderService.findAllNewWithFood();

        if(orders.size() == 0) return null;

        return orders.stream().map(OrderDTO::new).toList();
    }

    @GetMapping(value = "/forCook/all/{id}")
    public List<OrderDTO> findAllMyWithFood(@PathVariable Long id) {
        List<Order> orders = orderService.findAllMyWithFood(id);

        if(orders.size() == 0) return null;

        return orders.stream().map(OrderDTO::new).toList();
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO){

        Order order = orderService.createOrder(orderDTO);

        return new ResponseEntity<OrderDTO>(new OrderDTO(order), HttpStatus.CREATED);
    }

}
