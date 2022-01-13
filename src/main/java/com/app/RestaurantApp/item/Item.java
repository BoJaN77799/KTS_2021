package com.app.RestaurantApp.item;

import com.app.RestaurantApp.category.Category;
import com.app.RestaurantApp.enums.ItemType;
import com.app.RestaurantApp.ingredient.Ingredient;
import com.app.RestaurantApp.item.dto.ItemDTO;
import com.app.RestaurantApp.price.Price;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)

@SQLDelete(sql = "UPDATE item " + "SET deleted = true " + "WHERE id = ?")
@Where(clause = "deleted = false")

public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "cost", nullable = false)
    private double cost;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Price> prices;

    @ManyToMany(cascade = {CascadeType.DETACH}) // when food is deleted, all ingredients that are linked to
                                                // that food loses references without removing
    @JoinTable(
            name = "item_ingredient",
            joinColumns = {@JoinColumn(name = "item_id")},
            inverseJoinColumns = {@JoinColumn(name = "ingredient_id")}
    )
    private Set<Ingredient> ingredients;

    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;

    @Column( name="current_price")
    private Double currentPrice;

    @Enumerated(EnumType.STRING)
    @Column( name="item_type", nullable = false)
    private ItemType itemType;

    @Column(name = "deleted")
    private boolean deleted;

    public Item() {
    }

    public Item(ItemDTO itemDTO) {
        this.id = itemDTO.getId();
        this.name = itemDTO.getName();
        this.description = itemDTO.getDescription();
        this.image = itemDTO.getImage();
        this.cost = itemDTO.getCost();
        this.category = itemDTO.getCategory() != null ? new Category(itemDTO.getCategory()) : null;
        this.itemType = itemDTO.getItemType();
        this.deleted = itemDTO.isDeleted();
    }

    public Item(long id, String name, double currentPrice, double cost) {
        this.id = id;
        this.name = name;
        this.currentPrice = currentPrice;
        this.cost = cost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Set<Price> getPrices() {
        return prices;
    }

    public void setPrices(Set<Price> prices) {
        this.prices = prices;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
