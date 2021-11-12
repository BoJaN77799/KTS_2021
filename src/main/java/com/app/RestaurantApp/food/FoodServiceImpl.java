package com.app.RestaurantApp.food;

import com.app.RestaurantApp.category.Category;
import com.app.RestaurantApp.category.CategoryService;
import com.app.RestaurantApp.food.dto.FoodDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private FoodRepository foodRepository;

    @Override
    public Food findOne(Long id) {
        return foodRepository.findById(id).orElseGet(null);
    }

    @Override
    public Food saveFood(FoodDTO foodDTO) {
        Food food = new Food(foodDTO);
        if (foodDTO.getCategory() != null) {
            Category category = categoryService.findOne(foodDTO.getCategory().getId());
            if (category == null)
                // need to make category first
                category = categoryService.insertCategory(foodDTO.getCategory());
            food.setCategory(category);
        }
        foodRepository.save(food);
        return food;
    }

    @Override
    public void deleteFood(Food food) {
        foodRepository.delete(food);
    }
}
