package com.app.RestaurantApp.table;

import com.app.RestaurantApp.table.dto.*;
import com.app.RestaurantApp.users.UserException;
import com.app.RestaurantApp.users.appUser.AppUser;
import com.app.RestaurantApp.users.dto.AppUserAdminUserListDTO;
import com.app.RestaurantApp.users.dto.CreateUserDTO;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tables")
public class TableController {

    @Autowired
    private TableService tableService;

    @GetMapping(value = "/info", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN', 'WAITER')")
    public FloorTableInfo getRestaurantFloorTablesInfo() {
        return tableService.getFloorTableInfo();
    }

    @GetMapping(value = "/{floor}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public List<TableAdminDTO> getAllTablesFromFloorAdmin(@PathVariable(value = "floor") int floor) {
        List<Table> tables = tableService.getTablesFromFloor(floor);
        return tables.stream().map(TableAdminDTO::new).toList();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TableAdminDTO> createTable(@RequestBody TableCreateDTO tableCreateDTO){
        try{
            Table table = tableService.createTable(tableCreateDTO);
            return new ResponseEntity<TableAdminDTO>(new TableAdminDTO(table), HttpStatus.OK);
        }catch (TableException e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            System.out.println("Unknown error happened while creating table!");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateTable(@RequestBody TableUpdateDTO tableAdminDTO) {
        try{
            tableService.updateTable(tableAdminDTO);
        } catch (TableException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Unknown error happened while updating table!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Table " + tableAdminDTO.getId() + " updated!", HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteTable(@PathVariable(value = "id") Long id){
        try{
            tableService.deleteTable(id);
            return new ResponseEntity<>("Table deleted successfully", HttpStatus.OK);
        }catch (TableException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            return new ResponseEntity<>("Unknown error happened while deleting table!", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('WAITER')")
    public List<TableWaiterDTO> getTablesFromFloor(@RequestParam(value = "floor") int floor){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return tableService.getTablesWithActiveOrderIfItExists(floor, userDetails.getUsername());
    }

}
