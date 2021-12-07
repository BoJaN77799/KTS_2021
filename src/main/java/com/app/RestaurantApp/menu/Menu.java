package com.app.RestaurantApp.menu;

import com.app.RestaurantApp.item.Item;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "menu_item",
            joinColumns = { @JoinColumn(name = "menu_id") },
            inverseJoinColumns = { @JoinColumn(name = "item_id") }
    )
    private Set<Item> items;

    @Column(name = "active_menu", nullable = false)
    private boolean activeMenu;

    public Menu() {
    }

    public Menu(String name) {
        this.name = name;
        this.activeMenu = true;
        this.items = new HashSet<>();
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

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    public boolean isActiveMenu() {
        return activeMenu;
    }

    public void setActiveMenu(boolean activeMenu) {
        this.activeMenu = activeMenu;
    }
}
