package com.app.RestaurantApp.table;

import com.app.RestaurantApp.table.dto.TableAdminDTO;
import com.app.RestaurantApp.table.dto.TableUpdateDTO;
import com.app.RestaurantApp.table.dto.TableWaiterDTO;

import java.util.List;

public interface TableService {

    List<Table> getTablesFromFloor(int floor);

    void updateTable(TableUpdateDTO tableAdminDTO) throws TableException;

    void deleteTable(Long id) throws TableException;

    void createTable(Table table) throws TableException;

    Table findById(Long id);

    Table save(Table table);

    List<TableWaiterDTO> getTablesWithActiveOrderIfItExists(int floor);
}
