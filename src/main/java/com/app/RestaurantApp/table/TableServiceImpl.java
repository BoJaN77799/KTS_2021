package com.app.RestaurantApp.table;

import com.app.RestaurantApp.table.dto.FloorTableInfo;
import com.app.RestaurantApp.table.dto.TableCreateDTO;
import com.app.RestaurantApp.table.dto.TableUpdateDTO;
import com.app.RestaurantApp.table.dto.TableWaiterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class TableServiceImpl implements TableService{

    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private Environment env;

    @Override
    public FloorTableInfo getFloorTableInfo(){ //todo test, kasnije je dodata metoda
        String floors = env.getProperty("restaurant.floors");
        String maxTables = env.getProperty("restaurant.maxTablesPerFloor");
        if (floors == null){
            floors = "3";
        }
        if (maxTables == null){
            maxTables = "12";
        }
        int floorsInt, maxTablesInt = 0;
        try{
            floorsInt = Integer.parseInt(floors);
        }catch(NumberFormatException e){
            floorsInt = 3;
        }
        try{
            maxTablesInt = Integer.parseInt(maxTables);
        }catch(NumberFormatException e){
            maxTablesInt = 12;
        }

        return new FloorTableInfo(floorsInt, maxTablesInt);
    }

    @Override
    public List<Table> getTablesFromFloor(int floor){
        return tableRepository.findByFloorAndActive(floor, true);
    }

    @Override
    public Table updateTable(TableUpdateDTO tableAdminDTO) throws TableException{
        Optional<Table> tableOptional = tableRepository.findByIdAndActive(tableAdminDTO.getId(), true);
        if (tableOptional.isEmpty()) throw new TableException("Invalid table to update!");

        Table table = tableOptional.get();
        table.setX(tableAdminDTO.getX());
        table.setY(tableAdminDTO.getY());
        //todo videti jos kasnije na frontu da li ce postojati ogranicenje za X i Y kod pozicije stolova

        return tableRepository.save(table);
    }

    @Override
    public void deleteTable(Long id) throws TableException{
        //todo obrisati fizicki iz baze ako nema ordera vezanih za njega
        Optional<Table> tableOptional = tableRepository.findByIdAndActive(id, true);
        if (tableOptional.isEmpty()) throw new TableException("Invalid table to delete!");

        Table table = tableOptional.get();
        table.setActive(false);
        tableRepository.save(table);
    }

    @Override
    public Table createTable(TableCreateDTO tableDTO) throws TableException {
        String floors = env.getProperty("restaurant.floors");
        String maxTables = env.getProperty("restaurant.maxTablesPerFloor");
        if (floors == null) throw new TableException("Invalid property for number of floors!");
        if (maxTables == null) throw new TableException("Invalid property for max number of tables!");

        int floorsNum = 0;
        int maxTablesPerFloor = 0;
        try{
            floorsNum = Integer.parseInt(floors);
            maxTablesPerFloor = Integer.parseInt(maxTables);
        }catch (NumberFormatException e){
            throw new TableException("Invalid properties of restaurant!");
        }

        if (tableDTO.getFloor() < 0 || tableDTO.getFloor() > (floorsNum - 1)){
            throw new TableException("Invalid table floor (less than 0, or higher than max floor limit)!");
        }

        long tablesAtFloor = tableRepository.countByFloorAndActive(tableDTO.getFloor(), true);
        if (tablesAtFloor >= maxTablesPerFloor){
            throw new TableException("Can't add table! Too many tables on floor, limit is " + maxTablesPerFloor);
        }
        Table table = tableDTO.convertToTable();
        table.setActive(true);
        return tableRepository.save(table);
    }
    
    @Override
    public Table findById(Long id) {
        return tableRepository.findById(id).orElse(null);
    }

    @Override
    public Table save(Table table) {
        return tableRepository.save(table);
    }

    @Override
    public List<TableWaiterDTO> getTablesWithActiveOrderIfItExists(int floor, String waiterEmail) {
        List<TableWaiterDTO> resultList = new ArrayList<>();
        List<Long> listIds = new ArrayList<>();

        List<Table> listica1 = tableRepository.findByFloorAndInProgressOrders(floor);
        listica1.forEach((el) ->
            {
                resultList.add(new TableWaiterDTO(el, waiterEmail));
                listIds.add(el.getId());
            });
        List<Table> listica2 = new ArrayList<>();
        if (!listica1.isEmpty()) {
            listica2 = tableRepository.findByFloorAndNoInProgressOrders(floor, listIds);
        }else{
            listica2 = tableRepository.findByFloorAndActive(floor, true);
        }
        listica2.forEach((el) -> resultList.add(new TableWaiterDTO(el)));
        // znam da je uga buga da dva puta vraca iz baze, ali posto spring jpa ne podrzava 'with' prilikom fetchovanja...
        resultList.sort(Comparator.comparing(TableWaiterDTO::getId));
        return resultList;
    }
}
