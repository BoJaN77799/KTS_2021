package com.app.RestaurantApp.order;

import com.app.RestaurantApp.order.dto.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(consumes = "application/json")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO){

        Order order = orderService.createOrder(orderDTO);

        return new ResponseEntity<OrderDTO>(new OrderDTO(order), HttpStatus.CREATED);
    }

    @GetMapping(value = "/forUpdate/{id}")
    public ResponseEntity<OrderDTO> findOneWithOrderItemsForUpdate(@PathVariable Long id) {
        Order order = orderService.findOneWithOrderItemsForUpdate(id);

        if(order == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(new OrderDTO(order), HttpStatus.OK);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<OrderDTO> updateOrder(@RequestBody OrderDTO orderDTO){
        Order order = orderService.updateOrder(orderDTO);

        if(order == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(new OrderDTO(order), HttpStatus.OK);
    }

    @PutMapping(value = "/finish/{id}")
    public ResponseEntity<OrderDTO> finishOrder(@PathVariable Long id){
        Order order = orderService.finishOrder(id);

        if(order == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(new OrderDTO(order), HttpStatus.OK);
    }

}
