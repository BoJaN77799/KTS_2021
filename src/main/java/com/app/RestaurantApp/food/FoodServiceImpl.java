package com.app.RestaurantApp.food;

import com.app.RestaurantApp.category.Category;
import com.app.RestaurantApp.category.CategoryService;
import com.app.RestaurantApp.food.dto.FoodDTO;
import com.app.RestaurantApp.food.dto.FoodSearchDTO;
import com.app.RestaurantApp.food.dto.FoodWithIngredientsDTO;
import com.app.RestaurantApp.ingredient.IngredientRepository;
import com.app.RestaurantApp.ingredient.dto.IngredientDTO;
import com.app.RestaurantApp.item.ItemException;
import com.app.RestaurantApp.users.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private IngredientRepository ingredientRepository;

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

        String menu = (searchDTO != null) ? searchDTO.getMenu() : null;
        menu = (menu == null || menu.equals("")) ? "Stalni meni" : menu;

        foodsPage = foodRepository.findAllWithPriceByCriteria(name, category, type, menu, pageable);

        return foodsPage;
    }

    @Override
    public Food findOne(Long id) {
        return foodRepository.findById(id).orElse(null);
    }

    @Override
    public Food saveFood(Food food) { return foodRepository.save(food); }

    @Override
    public Food saveFood(FoodDTO foodDTO) throws ItemException, FoodException {
        Food food = new Food(foodDTO);
        FoodUtils.CheckFoodInfo(food);
        if (foodDTO.getCategory() != null) {
            Category category = categoryService.findOneByName(foodDTO.getCategory());
            if (category == null)
                // need to make category first
                category = categoryService.insertCategory(new Category(foodDTO.getCategory()));
            food.setCategory(category);
        }

        food.setMenu("Nedefinisan");
        food.setCurrentPrice(0.0);
        food = foodRepository.save(food);
        if(foodDTO.getMultipartImageFile() != null){
            try {
                String fileName = StringUtils.cleanPath(Objects.requireNonNull(foodDTO.getMultipartImageFile().getOriginalFilename()));
                String uploadDir = "food_photos/" + food.getId();

                FileUploadUtil.saveFile(uploadDir, fileName, foodDTO.getMultipartImageFile());

                food.setImage(uploadDir + "/" + fileName);

                foodRepository.save(food);
            }catch (NullPointerException | IOException e){
                System.out.println(e.getMessage());
                food.setImage(null);
            }
        }
        return food;
    }

    @Override
    public Food updateFood(FoodDTO foodDTO) throws ItemException, FoodException {
        Food food = new Food(foodDTO);
        FoodUtils.CheckFoodInfo(food);
        if (foodDTO.getCategory() != null) {
            Category category = categoryService.findOneByName(foodDTO.getCategory());
            if (category == null)
                // need to make category first
                category = categoryService.insertCategory(new Category(foodDTO.getCategory()));
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

    @Override
    public List<String> findAllFoodCategories() {
        return foodRepository.findAllFoodCategories();
    }

}