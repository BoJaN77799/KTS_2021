package com.app.RestaurantApp.table;

import com.app.RestaurantApp.table.dto.FloorTableInfo;
import com.app.RestaurantApp.table.dto.TableCreateDTO;
import com.app.RestaurantApp.table.dto.TableUpdateDTO;
import com.app.RestaurantApp.table.dto.TableWaiterDTO;

import java.util.List;

public interface TableService {

    List<Table> getTablesFromFloor(int floor);

    Table updateTable(TableUpdateDTO tableAdminDTO) throws TableException;

    void deleteTable(Long id) throws TableException;

    Table createTable(TableCreateDTO table) throws TableException;

    Table findById(Long id);

    Table save(Table table);

    List<TableWaiterDTO> getTablesWithActiveOrderIfItExists(int floor, String waiterEmail);

    FloorTableInfo getFloorTableInfo();

    TableWaiterDTO getTableOrderInfo(Long tableId, String email) throws TableException;
}
