package com.app.RestaurantApp.drinks.dto;
;
import com.app.RestaurantApp.category.dto.CategoryDTO;
import com.app.RestaurantApp.enums.ItemType;
import org.springframework.web.multipart.MultipartFile;

public class DrinkCreateDTO extends DrinkDTO {

    private MultipartFile multipartImageFile;

    public DrinkCreateDTO() {}

    public DrinkCreateDTO(Long id, String name, double cost, String description, String image, CategoryDTO category, ItemType itemType, boolean deleted, double volume) {
        super(id, name, cost, description, image, category.getName(),  itemType, deleted, volume);
    }

    public MultipartFile getMultipartImageFile() {
        return multipartImageFile;
    }

    public void setMultipartImageFile(MultipartFile multipartImageFile) {
        this.multipartImageFile = multipartImageFile;
    }
}
