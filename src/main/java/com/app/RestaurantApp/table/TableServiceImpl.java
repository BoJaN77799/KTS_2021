package com.app.RestaurantApp.table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableServiceImpl implements TableService{

    @Autowired
    private TableRepository tableRepository;

    public List<Table> getTablesFromFloor(int floor){
        return tableRepository.findByFloor(floor);
    }
}
