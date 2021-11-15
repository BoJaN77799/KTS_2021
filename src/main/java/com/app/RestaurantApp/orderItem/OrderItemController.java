package com.app.RestaurantApp.orderItem;

import com.app.RestaurantApp.orderItem.dto.OrderItemChangeStatusDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/orderItems")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;


    @PutMapping(value = "/changeStatus", consumes = "application/json")
    public ResponseEntity<OrderItemChangeStatusDTO> changeStatus(@RequestBody OrderItemChangeStatusDTO orderItemDTO){

        OrderItem orderItem = orderItemService.changeStatus(orderItemDTO);

        return new ResponseEntity<OrderItemChangeStatusDTO>(new OrderItemChangeStatusDTO(orderItem), HttpStatus.OK);
    }
}
