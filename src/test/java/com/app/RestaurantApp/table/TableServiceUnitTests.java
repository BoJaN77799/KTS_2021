package com.app.RestaurantApp.table;

import com.app.RestaurantApp.enums.OrderItemStatus;
import com.app.RestaurantApp.order.Order;
import com.app.RestaurantApp.orderItem.OrderItem;
import com.app.RestaurantApp.table.dto.TableCreateDTO;
import com.app.RestaurantApp.table.dto.TableUpdateDTO;
import com.app.RestaurantApp.table.dto.TableWaiterDTO;
import com.app.RestaurantApp.users.UserException;
import com.app.RestaurantApp.users.appUser.AppUser;
import com.app.RestaurantApp.users.dto.UpdateUserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.mock.env.MockEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.TestPropertySources;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import javax.naming.Context;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith({SpringExtension.class})
@SpringBootTest
//@TestPropertySource(properties = {"restaurant.floors", "restaurant.maxTablesPerFloor"})
public class TableServiceUnitTests {

    @MockBean
    TableRepository tableRepositoryMock;

    @Autowired
    TableService tableService;

    @Test
    public void testUpdateTable_InvalidID(){
        doReturn(Optional.empty()).when(tableRepositoryMock).findById(1L);

        TableUpdateDTO updateTableDTO = new TableUpdateDTO();
        updateTableDTO.setId(1L);

        TableException tableException = assertThrows(TableException.class, ()-> tableService.updateTable(updateTableDTO));
        assertEquals(tableException.getMessage(), "Invalid table to update!");
    }

    @Test
    public void testUpdateTable() throws TableException {
        Table table = getTableForUpdate();
        TableUpdateDTO tableUpdateDTO = new TableUpdateDTO(10L, 11, 22);

        doReturn(Optional.of(table)).when(tableRepositoryMock).findByIdAndActive(10L, true);

        tableService.updateTable(tableUpdateDTO);

        verify(tableRepositoryMock).save(table);
    }

    @Test
    public void testDeleteTable_InvalidTable(){
        doReturn(Optional.empty()).when(tableRepositoryMock).findByIdAndActive(1L, true);

        TableException tableException = assertThrows(TableException.class, ()-> tableService.deleteTable(1L));
        assertEquals(tableException.getMessage(), "Invalid table to delete!");
    }

    @Test
    public void testDeleteTable() throws TableException {
        Table table = new Table();
        doReturn(Optional.of(table)).when(tableRepositoryMock).findByIdAndActive(1L, true);

        tableService.deleteTable(1L);

        verify(tableRepositoryMock).save(table);
    }

    @Test
    public void testCreateTable_InvalidFloor(){
        TableCreateDTO table = new TableCreateDTO();
        table.setFloor(-1);

        TableException tableException = assertThrows(TableException.class, ()-> tableService.createTable(table));
        assertEquals(tableException.getMessage(), "Invalid table floor (less than 0, or higher than max floor limit)!");

        table.setFloor(3);

        tableException = assertThrows(TableException.class, ()-> tableService.createTable(table));
        assertEquals(tableException.getMessage(), "Invalid table floor (less than 0, or higher than max floor limit)!");
    }

    @Test
    public void testCreateTable_MaxNumberOfTablesReached(){
        TableCreateDTO table = new TableCreateDTO();
        table.setFloor(0);

        doReturn(12L).when(tableRepositoryMock).countByFloorAndActive(0, true);

        TableException tableException = assertThrows(TableException.class, ()-> tableService.createTable(table));
        assertEquals(tableException.getMessage(), "Can't add table! Too many tables on floor, limit is 10");
    }

    @Test
    public void testCreateTable() throws TableException {
        TableCreateDTO table = new TableCreateDTO();
        table.setFloor(0);

        doReturn(8L).when(tableRepositoryMock).countByFloorAndActive(0, true);

        tableService.createTable(table);
        verify(tableRepositoryMock).save(any());
    }

    @Test
    public void testGetTablesWithActiveOrderIfItExists(){
        //todo
//        doReturn(getListOfTablesWithActiveOrders()).when(tableRepositoryMock).findByFloorAndInProgressOrders(1);
//        doReturn(getListOfTablesWithoutActiveOrders()).when(tableRepositoryMock).findByFloorAndNoInProgressOrders(1);
//
//        List<TableWaiterDTO> list = tableService.getTablesWithActiveOrderIfItExists(1);
//
//        assertEquals(7, list.size());
//        assertFalse(list.get(0).isOccupied());
//        assertFalse(list.get(1).isOccupied());
//        assertFalse(list.get(2).isOccupied());
//        assertEquals("READY", list.get(3).getOrderStatus());
//        assertEquals("IN PROGRESS", list.get(4).getOrderStatus());
//        assertEquals("NEW", list.get(5).getOrderStatus());
//        assertEquals("FINISHABLE", list.get(6).getOrderStatus());
    }

    private Table getTableForUpdate(){
        Table table = new Table();
        table.setActive(true);
        table.setY(11);
        table.setX(22);
        table.setId(10L);
        table.setFloor(1);

        return table;
    }

    private Table getGenericTable(Long id){
        Table table = new Table();
        table.setActive(true);
        table.setY(11);
        table.setX(22);
        table.setId(10L);
        table.setFloor(1);
        table.setId(id);
        table.setOrders(new HashSet<>());

        return table;
    }

    private List<Table> getListOfTablesWithActiveOrders(){
        List<Table> tableList = new ArrayList<>();
        Table table1 = getGenericTable(4L);
        Table table2 = getGenericTable(5L);
        Table table3 = getGenericTable(6L);
        Table table4 = getGenericTable(7L);

        Order order1 = new Order();

        OrderItem oi1_1 = new OrderItem();
        oi1_1.setStatus(OrderItemStatus.FINISHED);
        OrderItem oi1_2 = new OrderItem();
        oi1_2.setStatus(OrderItemStatus.IN_PROGRESS);
        order1.setOrderItems(new HashSet<>());
        order1.getOrderItems().add(oi1_1);
        order1.getOrderItems().add(oi1_2);
        table1.getOrders().add(order1);

        Order order2 = new Order();

        OrderItem oi2_1 = new OrderItem();
        oi2_1.setStatus(OrderItemStatus.ORDERED);
        OrderItem oi2_2 = new OrderItem();
        oi2_2.setStatus(OrderItemStatus.IN_PROGRESS);
        order2.setOrderItems(new HashSet<>());
        order2.getOrderItems().add(oi2_1);
        order2.getOrderItems().add(oi2_2);
        table2.getOrders().add(order2);

        Order order3 = new Order();

        OrderItem oi3_1 = new OrderItem();
        oi3_1.setStatus(OrderItemStatus.ORDERED);
        OrderItem oi3_2 = new OrderItem();
        oi3_2.setStatus(OrderItemStatus.ORDERED);
        order3.setOrderItems(new HashSet<>());
        order3.getOrderItems().add(oi3_1);
        order3.getOrderItems().add(oi3_2);
        table3.getOrders().add(order3);

        Order order4 = new Order();

        OrderItem oi4_1 = new OrderItem();
        oi4_1.setStatus(OrderItemStatus.DELIVERED);
        OrderItem oi4_2 = new OrderItem();
        oi4_2.setStatus(OrderItemStatus.DELIVERED);
        order4.setOrderItems(new HashSet<>());
        order4.getOrderItems().add(oi4_1);
        order4.getOrderItems().add(oi4_2);
        table4.getOrders().add(order4);

        tableList.add(table1);
        tableList.add(table2);
        tableList.add(table3);
        tableList.add(table4);
        return tableList;
    }

    private List<Table> getListOfTablesWithoutActiveOrders(){
        List<Table> tableList = new ArrayList<>();
        tableList.add(getGenericTable(1L));
        tableList.add(getGenericTable(2L));
        tableList.add(getGenericTable(3L));
        return tableList;
    }
}
