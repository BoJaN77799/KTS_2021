package com.app.RestaurantApp.drinks;

import com.app.RestaurantApp.category.Category;
import com.app.RestaurantApp.category.CategoryService;
import com.app.RestaurantApp.drinks.dto.DrinkCreateDTO;
import com.app.RestaurantApp.drinks.dto.DrinkDTO;
import com.app.RestaurantApp.drinks.dto.DrinkSearchDTO;
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
public class DrinkServiceImpl implements DrinkService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private DrinkRepository drinkRepository;

    @Override
    public Page<Drink> getDrinksWithPrice(DrinkSearchDTO searchDTO, Pageable pageable) {
        Page<Drink> drinksPage;

        String name = (searchDTO != null) ? searchDTO.getName() : null;
        name = (name == null || name.equals("")) ? "ALL" : name;

        String category = (searchDTO != null) ? searchDTO.getCategory() : null;
        category = (category == null || category.equals("")) ? "ALL" : category;

        String menu = (searchDTO != null) ? searchDTO.getMenu() : null;
        menu = (menu == null || menu.equals("")) ? "Stalni meni" : menu;

        drinksPage = drinkRepository.findAllWithPriceByCriteria(name, category, menu, pageable);
        return drinksPage;
    }

    public Drink findOne(Long id) {
        return drinkRepository.findById(id).orElse(null);
    }

    @Override
    public Drink saveDrink(DrinkCreateDTO drinkDTO) throws DrinkException, ItemException {
        Drink drink = new Drink(drinkDTO);
        DrinkUtils.CheckDrinkInfo(drink);
        if (drinkDTO.getCategory() != null) {
            Category category = categoryService.findOneByName(drinkDTO.getCategory());
            if (category == null)
                // need to make category first
                category = categoryService.insertCategory(new Category(drinkDTO.getCategory()));
            drink.setCategory(category);
        }

        drink.setMenu("Nedefinisan");
        drink.setCurrentPrice(0.0);
        drink = drinkRepository.save(drink);

        if (drinkDTO.getMultipartImageFile() != null){
            try {
                String fileName = StringUtils.cleanPath(Objects.requireNonNull(drinkDTO.getMultipartImageFile().getOriginalFilename()));
                String uploadDir = "drink_photos/" + drink.getId();

                FileUploadUtil.saveFile(uploadDir, fileName, drinkDTO.getMultipartImageFile());

                drink.setImage(uploadDir + "/" + fileName);

                drinkRepository.save(drink);
            }catch (NullPointerException | IOException e){
                System.out.println(e.getMessage());
                drink.setImage(null);
            }
        }
        return drink;
    }

    @Override
    public void deleteDrink(Drink drink) {
        drinkRepository.delete(drink);
    }

    @Override
    public List<Drink> findAll() {
        return drinkRepository.findAll();
    }

    @Override
    public List<String> findAllDrinkCategories() {
        return drinkRepository.findAllDrinkCategories();
    }
}
