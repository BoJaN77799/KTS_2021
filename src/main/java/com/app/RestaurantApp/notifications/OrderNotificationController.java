package com.app.RestaurantApp.notifications;

import com.app.RestaurantApp.notifications.dto.OrderNotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/orderNotifications")
public class OrderNotificationController {

    @Autowired
    private OrderNotificationService orderNotificationService;

    @GetMapping(value = "/{employeeId}")
    @PreAuthorize("hasAnyRole('COOK', 'BARMAN', 'WAITER')")
    public ResponseEntity<List<OrderNotificationDTO>> findAllNotSeenForEmployee(@PathVariable Long employeeId) {
        List<OrderNotification> orderNotifications = orderNotificationService.findAllByEmployeeNotSeen(employeeId);
        List<OrderNotificationDTO> orderNotificationDTOs = new ArrayList<>();

        for(OrderNotification on : orderNotifications) {
            orderNotificationDTOs.add(new OrderNotificationDTO((on)));
        }

        return new ResponseEntity<>(orderNotificationDTOs, HttpStatus.OK);
    }

    @PutMapping(value = "/setSeenAll")
    @PreAuthorize("hasAnyRole('COOK', 'BARMAN', 'WAITER')")
    public ResponseEntity<String> setSeenAllNotificationsForEmployee(@RequestBody Long employeeId) {
        try {
            orderNotificationService.setSeenAllByEmployee(employeeId);
            return new ResponseEntity<>("All notifications set to seen.", HttpStatus.OK);
        } catch (OrderNotificationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Unknown error happened while updating notifications for employee.", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/setSeenOne")
    @PreAuthorize("hasAnyRole('COOK', 'BARMAN', 'WAITER')")
    public ResponseEntity<String> setSeen(@RequestBody Long id) {
        try {
            orderNotificationService.setSeen(id);
            return new ResponseEntity<>("Notification set to seen.", HttpStatus.OK);
        } catch (OrderNotificationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Unknown error happened while updating notification.", HttpStatus.BAD_REQUEST);
        }
    }
}
