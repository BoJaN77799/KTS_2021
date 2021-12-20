package com.app.RestaurantApp.item;

import com.app.RestaurantApp.item.dto.ItemDTO;
import com.app.RestaurantApp.item.dto.ItemPriceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping(value = "/getItem/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ItemDTO> getItemById(@PathVariable String id){
        Item i = itemService.findItemById(Long.valueOf(id));
        if (i == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>(new ItemDTO(i), HttpStatus.OK);
    }

    @PostMapping(value = "/createUpdatePriceOnItem")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<String> createUpdatePriceOnItem(@RequestBody ItemPriceDTO itemPriceDTO) {
        try {
            if (itemService.createUpdatePriceOnItem(itemPriceDTO))
                return new ResponseEntity<>("Price successfully created!", HttpStatus.CREATED);
            else
                return new ResponseEntity<>("Price successfully updated!", HttpStatus.OK);
        } catch (ItemException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Unknown error happened while creating/updating price on item !", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/getPricesOfItem/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<List<ItemPriceDTO>> getPricesOfItem(@PathVariable String id)  {
        try {
            List<ItemPriceDTO> prices = itemService.getPricesOfItem(id);
            if (prices.isEmpty())
                return new ResponseEntity<>(prices, HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(prices, HttpStatus.OK);
        } catch (ItemException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
