package com.app.RestaurantApp.food;

import com.app.RestaurantApp.food.dto.FoodDTO;
import com.app.RestaurantApp.food.dto.FoodSearchDTO;
import com.app.RestaurantApp.food.dto.FoodWithPriceDTO;
import com.app.RestaurantApp.item.ItemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import com.app.RestaurantApp.category.Category;
import com.app.RestaurantApp.category.CategoryService;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private FoodRepository foodRepository;

    @Override
    public Page<Food> getFoodWithPrice(FoodSearchDTO searchDTO, Pageable pageable) {
        Page<Food> foodsPage;

        String name = (searchDTO != null) ? searchDTO.getName() : null;
        name = (name == null || name.equals("")) ? "ALL" : name;

        String category = (searchDTO != null) ? searchDTO.getCategory() : null;
        category = (category == null || category.equals("")) ? "ALL" : category;

        String type = (searchDTO != null) ? searchDTO.getType() : null;
        type = (type == null || type.equals("")) ? "ALL" : type;

        foodsPage = foodRepository.findAllWithPriceByCriteria(name, category, type, pageable);

        return foodsPage;
    }

    @Override
    public Food findOne(Long id) {
        return foodRepository.findById(id).orElse(null);
    }

    @Override
    public Food saveFood(FoodDTO foodDTO) throws ItemException, FoodException {
        Food food = new Food(foodDTO);
        FoodUtils.CheckFoodInfo(food);
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

    @Override
    public List<Food> findAll() {
        return foodRepository.findAll();
    }
}
