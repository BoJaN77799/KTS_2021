package com.app.RestaurantApp.food;

import com.app.RestaurantApp.food.dto.FoodDTO;
import com.app.RestaurantApp.food.dto.FoodSearchDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Override
    public List<FoodDTO> getFood(FoodSearchDTO searchDTO, Pageable pageable) {
        List<Food> foods = new ArrayList<>();

        String name = (searchDTO != null) ? searchDTO.getName() : null;
        name = (name != null) ? name : "ALL";

        String category = (searchDTO != null) ? searchDTO.getCategory() : null;
        category = (category != null) ? category : "ALL";

        String type = (searchDTO != null) ? searchDTO.getType() : null;
        type = (type != null) ? type : "ALL";

        foods = foodRepository.findAllByCriteria(name, category, type, pageable);

        List<FoodDTO> foodsDTO = new ArrayList<>();
        foods.forEach(food -> foodsDTO.add(new FoodDTO(food)));

        return foodsDTO;
    }

}
