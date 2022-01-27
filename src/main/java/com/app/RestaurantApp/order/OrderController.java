package com.app.RestaurantApp.order;

import com.app.RestaurantApp.ControllerUtils;
import com.app.RestaurantApp.order.dto.OrderDTO;
import com.app.RestaurantApp.order.dto.OrderFindDTO;
import com.app.RestaurantApp.order.dto.OrderTableViewDTO;
import com.app.RestaurantApp.order.dto.SimpleOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

        if (order == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(new OrderDTO(order), HttpStatus.OK);
    }

    @GetMapping(value = "/forCook/{id}")
    @PreAuthorize("hasRole('COOK')")
    public ResponseEntity<OrderFindDTO> findOneWithFood(@PathVariable Long id) {
        Order order = orderService.findOneWithFood(id);

        if (order == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(new OrderFindDTO(order), HttpStatus.OK);
    }

    @GetMapping(value = "/forCook/all")
    @PreAuthorize("hasRole('COOK')")
    public ResponseEntity<List<OrderFindDTO>> findAllNewWithFood(Pageable pageable) {
        Page<Order> orders = orderService.findAllNewWithFood(pageable);
        if (orders.isEmpty())
            return new ResponseEntity<>(orders.stream().map(OrderFindDTO::new).toList(), ControllerUtils.createPageHeaderAttributes(orders), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(orders.stream().map(OrderFindDTO::new).toList(), ControllerUtils.createPageHeaderAttributes(orders), HttpStatus.OK);
    }

    @GetMapping(value = "/forCook/all/{id}")
    @PreAuthorize("hasRole('COOK')")
    public ResponseEntity<List<OrderFindDTO>> findAllMyWithFood(@PathVariable Long id, Pageable pageable) {
        Page<Order> orders = orderService.findAllMyWithFood(id, pageable);
        if (orders.isEmpty())
            return new ResponseEntity<>(orders.stream().map(OrderFindDTO::new).toList(), ControllerUtils.createPageHeaderAttributes(orders), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(orders.stream().map(OrderFindDTO::new).toList(), ControllerUtils.createPageHeaderAttributes(orders), HttpStatus.OK);
    }

    @GetMapping(value = "/forBarman/{id}")
    @PreAuthorize("hasRole('BARMAN')")
    public ResponseEntity<OrderFindDTO> findOneWithDrinks(@PathVariable Long id) {
        Order order = orderService.findOneWithDrinks(id);

        if (order == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(new OrderFindDTO(order), HttpStatus.OK);
    }

    @GetMapping(value = "/forBarman/all")
    @PreAuthorize("hasRole('BARMAN')")
    public ResponseEntity<List<OrderFindDTO>> findAllNewWithDrinks(Pageable pageable) {
        Page<Order> orders = orderService.findAllNewWithDrinks(pageable);
        if (orders.isEmpty())
            return new ResponseEntity<>(orders.stream().map(OrderFindDTO::new).toList(), ControllerUtils.createPageHeaderAttributes(orders), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(orders.stream().map(OrderFindDTO::new).toList(), ControllerUtils.createPageHeaderAttributes(orders), HttpStatus.OK);
    }

    @GetMapping(value = "/forBarman/all/{id}")
    @PreAuthorize("hasRole('BARMAN')")
    public ResponseEntity<List<OrderFindDTO>> findAllMyWithDrinks(@PathVariable Long id, Pageable pageable) {
        Page<Order> orders = orderService.findAllMyWithDrinks(id, pageable);
        if (orders.isEmpty())
            return new ResponseEntity<>(orders.stream().map(OrderFindDTO::new).toList(), ControllerUtils.createPageHeaderAttributes(orders), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(orders.stream().map(OrderFindDTO::new).toList(), ControllerUtils.createPageHeaderAttributes(orders), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasRole('WAITER')")
    public ResponseEntity<String> createOrder(@RequestBody OrderDTO orderDTO) {
        try {
            orderService.createOrder(orderDTO);
            return new ResponseEntity<>("Order successfully created.", HttpStatus.CREATED);
        } catch (OrderException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Unknown error happened while creating order!", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "accept")
    @PreAuthorize("hasAnyRole('COOK', 'BARMAN')")
    public ResponseEntity<String> acceptOrder(@RequestParam Long id, @RequestParam String email) {
        try {
            orderService.acceptOrder(id, email);
            return new ResponseEntity<>("Order successfully accepted!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/forUpdate/{id}")
    @PreAuthorize("hasRole('WAITER')")
    public ResponseEntity<OrderDTO> findOneWithOrderItemsForUpdate(@PathVariable Long id) {
        Order order = orderService.findOneWithOrderItemsForUpdate(id);

        if (order == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(new OrderDTO(order), HttpStatus.OK);
    }

    @PutMapping(consumes = "application/json")
    @PreAuthorize("hasRole('WAITER')")
    public ResponseEntity<String> updateOrder(@RequestBody OrderDTO orderDTO) {
        try {
            orderService.updateOrder(orderDTO);
            return new ResponseEntity<>("Order successfully updated.", HttpStatus.OK);
        } catch (OrderException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Unknown error happened while updating order!", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/finish")
    @PreAuthorize("hasRole('WAITER')")
    public ResponseEntity<String> finishOrder(@RequestBody Long id) {
        Order order = orderService.finishOrder(id);

        if (order == null) return new ResponseEntity<>("Order not found!", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>("Order successfully finished.", HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    @PreAuthorize("hasAnyRole('COOK', 'BARMAN')")
    public ResponseEntity<List<SimpleOrderDTO>> searchOrders(@RequestParam(value = "searchField", required = false) String searchField,
                                                             @RequestParam(value = "orderStatus", required = false) String orderStatus,
                                                             Pageable pageable) {
        Page<Order> orders = orderService.searchOrders(searchField, orderStatus, pageable);
        if (orders.isEmpty())
            return new ResponseEntity<>(orders.stream().map(SimpleOrderDTO::new).toList(), ControllerUtils.createPageHeaderAttributes(orders), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(orders.stream().map(SimpleOrderDTO::new).toList(), ControllerUtils.createPageHeaderAttributes(orders), HttpStatus.OK);
    }

    @GetMapping(value = "/getOrderForTable/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderTableViewDTO> getOrderForTable(@PathVariable("id") Long id) {
        Order order = orderService.findOrderAtTable(id);
        if (order == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(new OrderTableViewDTO(order), HttpStatus.OK);
    }

}
