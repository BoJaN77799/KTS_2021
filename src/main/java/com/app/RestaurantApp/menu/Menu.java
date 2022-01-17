package com.app.RestaurantApp.menu;

import javax.persistence.*;

@Entity
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "active_menu", nullable = false)
    private boolean activeMenu;

    public Menu(String name) {
        this.name = name;
        this.activeMenu = true;
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

    public boolean isActiveMenu() {
        return activeMenu;
    }

    public void setActiveMenu(boolean activeMenu) {
        this.activeMenu = activeMenu;
    }
}
