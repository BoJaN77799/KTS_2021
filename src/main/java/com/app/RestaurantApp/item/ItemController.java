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
    public ResponseEntity<String> createUpdatePriceOnItem(@RequestBody ItemPriceDTO itemPriceDTO) throws ItemException {
        if (itemService.createUpdatePriceOnItem(itemPriceDTO))
            return new ResponseEntity<>("Price successfully created!", HttpStatus.OK);
        else
            return new ResponseEntity<>("Price successfully updated!", HttpStatus.OK);
    }

    @GetMapping(value = "/getPricesOfItem/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public List<ItemPriceDTO> getPricesOfItem(@PathVariable String id) throws ItemException {
        return itemService.getPricesOfItem(id);
    }
}
