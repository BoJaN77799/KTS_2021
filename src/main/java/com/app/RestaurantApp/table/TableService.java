package com.app.RestaurantApp.table;

public interface TableService {

    Table findById(Long id);

    Table save(Table table);

}
