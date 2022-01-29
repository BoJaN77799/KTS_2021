package com.app.RestaurantApp.table;

import com.app.RestaurantApp.enums.OrderItemStatus;
import com.app.RestaurantApp.enums.OrderStatus;
import com.app.RestaurantApp.order.Order;
import com.app.RestaurantApp.orderItem.OrderItem;
import com.app.RestaurantApp.table.dto.TableCreateDTO;
import com.app.RestaurantApp.table.dto.TableUpdateDTO;
import com.app.RestaurantApp.table.dto.TableWaiterDTO;
import com.app.RestaurantApp.users.UserException;
import com.app.RestaurantApp.users.appUser.AppUser;
import com.app.RestaurantApp.users.dto.UpdateUserDTO;
import com.app.RestaurantApp.users.employee.Employee;
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
import static org.mockito.Mockito.*;

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
        doReturn(getListOfTablesWithActiveOrders()).when(tableRepositoryMock).findByFloorAndInProgressOrders(1);
        doReturn(getListOfTablesWithoutActiveOrders()).when(tableRepositoryMock).findByFloorAndNoInProgressOrders(1, getIdsOfTablesWithActiveOrders());

        List<TableWaiterDTO> list = tableService.getTablesWithActiveOrderIfItExists(1, "waiter@maildrop.cc");

        assertEquals(7, list.size());
        assertFalse(list.get(0).isOccupied());
        assertFalse(list.get(1).isOccupied());
        assertFalse(list.get(2).isOccupied());
        assertEquals("READY", list.get(3).getOrderStatus());
        assertEquals("CREATED", list.get(4).getOrderStatus());
        assertEquals("CREATED", list.get(5).getOrderStatus());
        assertEquals("DELIVERED", list.get(6).getOrderStatus());
    }

    @Test
    public void testGetTableOrderInfo()  throws TableException {
        doReturn(Optional.of(getTableWithActiveOrder())).when(tableRepositoryMock).findTableByIdIfHasActiveOrders(1L);

        TableWaiterDTO tableWaiterDTO = tableService.getTableOrderInfo(1L, "mirkolegenda@gmail.com");
        assertNotNull(tableWaiterDTO);
        assertEquals("READY", tableWaiterDTO.getOrderStatus());
        assertTrue(tableWaiterDTO.isOccupied());
        assertEquals(1L, tableWaiterDTO.getId());

        doReturn(Optional.empty()).when(tableRepositoryMock).findTableByIdIfHasActiveOrders(2L);
        doReturn(Optional.of(getBasicTable())).when(tableRepositoryMock).findByIdAndActive(2L, true);
        tableWaiterDTO = tableService.getTableOrderInfo(2L, "mirkolegenda@gmail.com");
        assertNotNull(tableWaiterDTO);
        assertEquals(tableWaiterDTO.getOrderStatus(), "TABLE FREE");
        assertFalse(tableWaiterDTO.isOccupied());
        assertEquals(2L, tableWaiterDTO.getId());
    }

    @Test
    public void testGetTableOrderInfo_noTable()  {
        doReturn(Optional.empty()).when(tableRepositoryMock).findTableByIdIfHasActiveOrders(2L);
        doReturn(Optional.empty()).when(tableRepositoryMock).findByIdAndActive(2L, true);
        TableException tableException = assertThrows(TableException.class, ()-> tableService.getTableOrderInfo(2L, "mirkolegenda@gmail.com"));
        assertEquals(tableException.getMessage(), "Table not found!");
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

    private List<Long> getIdsOfTablesWithActiveOrders(){
        return List.of(4L, 5L, 6L, 7L);
    }

    private Table getTableWithActiveOrder() {
        Table table = new Table();
        table.setId(1L);
        table.setActive(true);
        table.setFloor(1);
        table.setX(100);
        table.setY(100);
        table.setOrders(new HashSet<>());

        Employee mirko = new Employee();
        mirko.setEmail("mirkolegenda@gmail.com");

        Order order1 = new Order();
        order1.setStatus(OrderStatus.IN_PROGRESS);
        order1.setWaiter(mirko);

        OrderItem oi1_1 = new OrderItem();
        oi1_1.setStatus(OrderItemStatus.FINISHED);
        OrderItem oi1_2 = new OrderItem();
        oi1_2.setStatus(OrderItemStatus.IN_PROGRESS);
        HashSet<OrderItem> order_items1 = new HashSet<OrderItem>();
        order_items1.add(oi1_1);
        order_items1.add(oi1_2);
        order1.setOrderItems(order_items1);
        table.getOrders().add(order1);

        return table;
    }

    private Table getBasicTable() {
        Table table = new Table();
        table.setId(2L);
        table.setActive(true);
        table.setFloor(1);
        table.setX(100);
        table.setY(100);
        table.setOrders(new HashSet<>());

        return table;
    }

    private List<Table> getListOfTablesWithActiveOrders(){
        List<Table> tableList = new ArrayList<>();
        Table table1 = getGenericTable(4L);
        Table table2 = getGenericTable(5L);
        Table table3 = getGenericTable(6L);
        Table table4 = getGenericTable(7L);

        Employee mirko = new Employee();
        mirko.setEmail("mirkolegenda@gmail.com");

        Order order1 = mock(Order.class);
        doReturn(mirko).when(order1).getWaiter();

        OrderItem oi1_1 = new OrderItem();
        oi1_1.setStatus(OrderItemStatus.FINISHED);
        OrderItem oi1_2 = new OrderItem();
        oi1_2.setStatus(OrderItemStatus.IN_PROGRESS);
        HashSet<OrderItem> order_items1 = new HashSet<OrderItem>();
        order_items1.add(oi1_1);
        order_items1.add(oi1_2);
        doReturn(order_items1).when(order1).getOrderItems();
        table1.getOrders().add(order1);

        Order order2 = mock(Order.class);
        doReturn(mirko).when(order2).getWaiter();

        OrderItem oi2_1 = new OrderItem();
        oi2_1.setStatus(OrderItemStatus.ORDERED);
        OrderItem oi2_2 = new OrderItem();
        oi2_2.setStatus(OrderItemStatus.IN_PROGRESS);
        HashSet<OrderItem> order_items2 = new HashSet<OrderItem>();
        order_items2.add(oi2_1);
        order_items2.add(oi2_2);
        doReturn(order_items2).when(order2).getOrderItems();
        table2.getOrders().add(order2);

        Order order3 = mock(Order.class);
        doReturn(mirko).when(order3).getWaiter();

        OrderItem oi3_1 = new OrderItem();
        oi3_1.setStatus(OrderItemStatus.ORDERED);
        OrderItem oi3_2 = new OrderItem();
        oi3_2.setStatus(OrderItemStatus.ORDERED);
        HashSet<OrderItem> order_items3 = new HashSet<OrderItem>();
        order_items3.add(oi3_1);
        order_items3.add(oi3_2);
        doReturn(order_items3).when(order3).getOrderItems();
        table3.getOrders().add(order3);

        Order order4 = mock(Order.class);
        doReturn(mirko).when(order4).getWaiter();

        OrderItem oi4_1 = new OrderItem();
        oi4_1.setStatus(OrderItemStatus.DELIVERED);
        OrderItem oi4_2 = new OrderItem();
        oi4_2.setStatus(OrderItemStatus.DELIVERED);
        HashSet<OrderItem> order_items4 = new HashSet<OrderItem>();
        order_items3.add(oi4_1);
        order_items3.add(oi4_2);
        doReturn(order_items4).when(order4).getOrderItems();
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
