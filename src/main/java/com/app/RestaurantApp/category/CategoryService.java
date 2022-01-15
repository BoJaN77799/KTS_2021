package com.app.RestaurantApp.category;


public interface CategoryService {

    Category findOne(Long id);
    Category findOneByName(String name);
    Category insertCategory(Category category);
}
