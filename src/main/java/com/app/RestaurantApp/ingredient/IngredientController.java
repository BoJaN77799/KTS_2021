package com.app.RestaurantApp.ingredient;

import com.app.RestaurantApp.food.Food;
import com.app.RestaurantApp.food.dto.FoodDTO;
import com.app.RestaurantApp.ingredient.dto.IngredientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/ingredients")
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    @GetMapping(value = "/all")
    @PreAuthorize("hasRole('HEAD_COOK')")
    public ResponseEntity<List<IngredientDTO>> findAll() {
        List<Ingredient> ingredientList = ingredientService.getAll();
        if (ingredientList == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ingredientList.stream().map(IngredientDTO::new).toList(), HttpStatus.OK);
    }
}
