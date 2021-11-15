package com.app.RestaurantApp.table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TableServiceImpl implements TableService{

    @Autowired
    private TableRepository tableRepository;

    @Override
    public Table findById(Long id) {
        return tableRepository.findById(id).orElse(null);
    }

    @Override
    public Table save(Table table) {
        return tableRepository.save(table);
    }

}
