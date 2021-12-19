package com.app.RestaurantApp.table;

import com.app.RestaurantApp.table.dto.TableCreateDTO;
import com.app.RestaurantApp.table.dto.TableUpdateDTO;
import com.app.RestaurantApp.table.dto.TableWaiterDTO;
import com.app.RestaurantApp.users.UserException;
import com.app.RestaurantApp.users.appUser.AppUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

import static com.app.RestaurantApp.users.appUser.Constants.UPDATE_LASTNAME;
import static com.app.RestaurantApp.users.appUser.Constants.UPDATE_NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({SpringExtension.class})
@SpringBootTest
public class TableServiceIntegrationTests {

    @Autowired
    TableService tableService;

    @Autowired
    TableRepository tableRepository;

    @Test
    public void testGetTablesFromFloor(){
        List<Table> tableList = tableService.getTablesFromFloor(0);
        assertEquals(12, tableList.size());
        assertThat(tableList).extracting("active").containsOnly(true);
        assertThat(tableList).extracting("floor").containsOnly(0);

        tableList = tableService.getTablesFromFloor(1);
        assertEquals(4, tableList.size());
        assertThat(tableList).extracting("active").containsOnly(true);
        assertThat(tableList).extracting("floor").containsOnly(1);

        tableList = tableService.getTablesFromFloor(2);
        assertEquals(0, tableList.size());
    }

    @Test
    public void testUpdateTable(){
        TableUpdateDTO tableUpdateDTO = new TableUpdateDTO(1L, 90, 92);

        try {
            Table table = tableService.updateTable(tableUpdateDTO);

            assertEquals(1L, table.getId());
            assertEquals(90, table.getX());
            assertEquals(92, table.getY());
        } catch (TableException e) {
            fail();
        }
    }

    @Test
    public void testDeleteTable(){
        try {
            long id = 1L;
            tableService.deleteTable(id);
            Table table = tableService.findById(1L);
            assertFalse(table.getActive());

            //ponistavanje brisanja
            table.setActive(true);
            tableRepository.save(table);
        } catch (TableException e) {
            fail();
        }
    }

    @Test @Transactional
    public void testCreateTable() throws TableException {
        TableCreateDTO tableCreateDTO = new TableCreateDTO();
        tableCreateDTO.setFloor(1);
        tableCreateDTO.setX(11);
        tableCreateDTO.setY(90);

        Table table = tableService.createTable(tableCreateDTO);

        assertEquals(1, table.getFloor());
        assertEquals(11, table.getX());
        assertEquals(90, table.getY());
    }

    @Test @Transactional
    public void testCreateTable_MaxNumberOfTablesReached() {
        TableCreateDTO tableCreateDTO = new TableCreateDTO();
        tableCreateDTO.setFloor(0);
        tableCreateDTO.setX(11);
        tableCreateDTO.setY(90);

        TableException tableException = assertThrows(TableException.class, ()-> tableService.createTable(tableCreateDTO));
        assertEquals(tableException.getMessage(), "Can't add table! Too many tables on floor, limit is 10");
    }

    @Test
    public void testGetTablesWithActiveOrderIfItExists(){
        List<TableWaiterDTO> tableList = tableService.getTablesWithActiveOrderIfItExists(0);
        tableList.sort(Comparator.comparing(TableWaiterDTO::getId));
        assertEquals("NEW", tableList.get(0).getOrderStatus());
        assertEquals("NEW", tableList.get(1).getOrderStatus());
        assertEquals("NEW", tableList.get(2).getOrderStatus());
        assertEquals("NEW", tableList.get(3).getOrderStatus());
        assertEquals("NEW", tableList.get(4).getOrderStatus());
        assertEquals("TABLE FREE", tableList.get(5).getOrderStatus());
        assertEquals("TABLE FREE", tableList.get(6).getOrderStatus());
        assertEquals("TABLE FREE", tableList.get(7).getOrderStatus());
        assertEquals("NEW", tableList.get(8).getOrderStatus());
        assertEquals("READY", tableList.get(9).getOrderStatus());
        assertEquals("TABLE FREE", tableList.get(10).getOrderStatus());
        assertEquals("TABLE FREE", tableList.get(11).getOrderStatus());

        tableList = tableService.getTablesWithActiveOrderIfItExists(1);
        assertEquals(4, tableList.size());
        assertThat(tableList).extracting("occupied").containsOnly(false);
    }
}
