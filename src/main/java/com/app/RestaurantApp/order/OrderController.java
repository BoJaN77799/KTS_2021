package com.app.RestaurantApp.order;

import com.app.RestaurantApp.order.dto.OrderDTO;
import com.app.RestaurantApp.order.dto.OrderTableViewDTO;
import com.app.RestaurantApp.order.dto.SimpleOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
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

        if(order == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(new OrderDTO(order), HttpStatus.OK);
    }

    @GetMapping(value = "/forCook/{id}")
    @PreAuthorize("hasRole('COOK')")
    public ResponseEntity<OrderDTO> findOneWithFood(@PathVariable Long id) {
        Order order = orderService.findOneWithFood(id);

        if(order == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(new OrderDTO(order), HttpStatus.OK);
    }

    @GetMapping(value = "/forCook/all")
    @PreAuthorize("hasRole('COOK')")
    public List<OrderDTO> findAllNewWithFood() {
        List<Order> orders = orderService.findAllNewWithFood();

        if(orders.size() == 0) return null;

        return orders.stream().map(OrderDTO::new).toList();
    }

    @GetMapping(value = "/forCook/all/{id}")
    @PreAuthorize("hasRole('COOK')")
    public List<OrderDTO> findAllMyWithFood(@PathVariable Long id) {
        List<Order> orders = orderService.findAllMyWithFood(id);

        if(orders.size() == 0) return null;

        return orders.stream().map(OrderDTO::new).toList();
    }

    @GetMapping(value = "/forBarman/{id}")
    @PreAuthorize("hasRole('BARMAN')")
    public ResponseEntity<OrderDTO> findOneWithDrinks(@PathVariable Long id) {
        Order order = orderService.findOneWithDrinks(id);

        if(order == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(new OrderDTO(order), HttpStatus.OK);
    }

    @GetMapping(value = "/forBarman/all")
    @PreAuthorize("hasRole('BARMAN')")
    public List<OrderDTO> findAllNewWithDrinks() {
        List<Order> orders = orderService.findAllNewWithDrinks();

        if(orders.size() == 0) return null;

        return orders.stream().map(OrderDTO::new).toList();
    }

    @GetMapping(value = "/forBarman/all/{id}")
    @PreAuthorize("hasRole('BARMAN')")
    public List<OrderDTO> findAllMyWithDrinks(@PathVariable Long id) {
        List<Order> orders = orderService.findAllMyWithDrinks(id);

        if(orders.size() == 0) return null;

        return orders.stream().map(OrderDTO::new).toList();
    }

    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasRole('WAITER')")
    public ResponseEntity<String> createOrder(@RequestBody OrderDTO orderDTO){
        try {
            orderService.createOrder(orderDTO);
            return new ResponseEntity<>("Order successfully created.", HttpStatus.CREATED);
        }
        catch (OrderException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e){
            return new ResponseEntity<>("Unknown error happened while creating order!", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value="accept/{id}/by/{email}")
    @PreAuthorize("hasAnyRole('COOK', 'BARMAN')")
    public ResponseEntity<String> acceptOrder(@PathVariable Long id, @PathVariable String email){
        try {
            orderService.acceptOrder(id, email);
            return new ResponseEntity<>("Order successfully accepted", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/forUpdate/{id}")
    @PreAuthorize("hasRole('WAITER')")
    public ResponseEntity<OrderDTO> findOneWithOrderItemsForUpdate(@PathVariable Long id) {
        Order order = orderService.findOneWithOrderItemsForUpdate(id);

        if(order == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(new OrderDTO(order), HttpStatus.OK);
    }

    @PutMapping(consumes = "application/json")
    @PreAuthorize("hasRole('WAITER')")
    public ResponseEntity<String> updateOrder(@RequestBody OrderDTO orderDTO){
        try {
            orderService.updateOrder(orderDTO);
            return new ResponseEntity<>("Order successfully updated.", HttpStatus.OK);
        }
        catch (OrderException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            return new ResponseEntity<>("Unknown error happened while updating order!", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/finish/{id}")
    @PreAuthorize("hasRole('WAITER')")
    public ResponseEntity<String> finishOrder(@PathVariable Long id){
        Order order = orderService.finishOrder(id);

        if(order == null) return new ResponseEntity<>("Order not found!", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>("Order successfully finished.", HttpStatus.OK);
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('COOK', 'BARMAN')")
    public ResponseEntity<List<SimpleOrderDTO>> searchOrders(@RequestParam(value = "searchField", required = false) String searchField,
                                             @RequestParam(value = "orderStatus", required = false) String orderStatus,
                                             Pageable pageable) {
        Page<Order> orders = orderService.searchOrders(searchField, orderStatus, pageable);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("total-elements", Long.toString(orders.getTotalElements()));
        responseHeaders.set("total-pages", Long.toString(orders.getTotalPages()));
        responseHeaders.set("current-page", Integer.toString(orders.getNumber()));

        if (orders.isEmpty())
            return new ResponseEntity<>(orders.stream().map(SimpleOrderDTO::new).toList(), responseHeaders, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(orders.stream().map(SimpleOrderDTO::new).toList(), responseHeaders, HttpStatus.OK);
    }

    @GetMapping(value = "/getOrderForTable/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderTableViewDTO> getOrderForTable(@PathVariable("id") Long id) {
        Order order = orderService.findOrderAtTable(id);
        if (order == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(new OrderTableViewDTO(order), HttpStatus.BAD_REQUEST);
    }

}
