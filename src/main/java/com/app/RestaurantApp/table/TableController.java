package com.app.RestaurantApp.table;

import com.app.RestaurantApp.table.dto.TableAdminDTO;
import com.app.RestaurantApp.users.appUser.AppUser;
import com.app.RestaurantApp.users.dto.AppUserAdminUserListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/tables")
public class TableController {

    @Autowired
    private TableService tableService;

    @GetMapping(value = "/{floor}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TableAdminDTO> getAllTablesFromFloorAdmin(@PathVariable(value = "floor") int floor) {
        List<Table> tables = tableService.getTablesFromFloor(floor);
        return tables.stream().map(TableAdminDTO::new).toList();
    }

}
