package com.app.RestaurantApp.orderItem;

import com.app.RestaurantApp.orderItem.dto.OrderItemChangeStatusDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/orderItems")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;


    @PutMapping(value = "/changeStatus", consumes = "application/json")
    @PreAuthorize("hasAnyRole('COOK', 'BARMAN', 'WAITER')")
    public ResponseEntity<String> changeStatus(@RequestBody OrderItemChangeStatusDTO orderItemDTO) {
        try {
            orderItemService.changeStatus(orderItemDTO);
            return new ResponseEntity<>("Order item status successfully changed.", HttpStatus.OK);
        } catch (OrderItemException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Unknown error happened while changing status of order item!", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/deliver/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('WAITER')")
    public ResponseEntity<String> deliverOrderItemWaiter(@PathVariable(value = "id") Long id) {
        try {
            orderItemService.deliverOrderItem(id);
            return new ResponseEntity<>("Order item delivered!", HttpStatus.OK);
        } catch (OrderItemException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Unknown error happened while delivering order item!", HttpStatus.BAD_REQUEST);
        }
    }
}
