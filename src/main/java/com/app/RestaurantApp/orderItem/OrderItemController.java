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
    public ResponseEntity<String> changeStatus(@RequestBody OrderItemChangeStatusDTO orderItemDTO){
        try {
            orderItemService.changeStatus(orderItemDTO);
            return new ResponseEntity<>("Order item status successfully changed.", HttpStatus.BAD_REQUEST);
        }
        catch (OrderItemException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            return new ResponseEntity<>("Unknown error happened while changing status of order item!", HttpStatus.BAD_REQUEST);
        }
    }
}
