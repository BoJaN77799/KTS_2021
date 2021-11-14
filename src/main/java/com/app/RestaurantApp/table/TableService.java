package com.app.RestaurantApp.table;

import java.util.List;

public interface TableService {

    List<Table> getTablesFromFloor(int floor);

}
