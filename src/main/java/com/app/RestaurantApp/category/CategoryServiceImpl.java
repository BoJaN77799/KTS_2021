package com.app.RestaurantApp.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category findOne(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Category findOneByName(String name) {
        return categoryRepository.findOneByName(name).orElse(null);
    }

    @Override
    public Category insertCategory(Category category) {
        return categoryRepository.save(category);
    }
}
