package com.app.RestaurantApp.table;

import com.app.RestaurantApp.enums.OrderStatus;
import com.app.RestaurantApp.order.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@DataJpaTest
public class TableRepositoryTests {

    @Autowired
    private TableRepository tableRepository;

    @Test
    public void testFindByFloorAndActive(){
        List<Table> tableList = tableRepository.findByFloorAndActive(0, true);
        assertEquals(12, tableList.size());
        assertThat(tableList).extracting("active").containsOnly(true);
        assertThat(tableList).extracting("floor").containsOnly(0);

        tableList = tableRepository.findByFloorAndActive(1, true);
        assertEquals(4, tableList.size());
        assertThat(tableList).extracting("active").containsOnly(true);
        assertThat(tableList).extracting("floor").containsOnly(1);

        tableList = tableRepository.findByFloorAndActive(1, false);
        assertEquals(1, tableList.size());
        assertEquals(false, tableList.get(0).getActive());

        tableList = tableRepository.findByFloorAndActive(2, true);
        assertEquals(0, tableList.size());
    }

    @Test
    public void testCountByFloorAndActive(){
        long count = tableRepository.countByFloorAndActive(0, true);
        assertEquals(12, count);

        count = tableRepository.countByFloorAndActive(1, true);
        assertEquals(4, count);

        count = tableRepository.countByFloorAndActive(1, false);
        assertEquals(1, count);

        count = tableRepository.countByFloorAndActive(2, true);
        assertEquals(0, count);
    }

    @Test
    public void testFindByFloorAndInProgressOrders(){
        List<Table> tal = tableRepository.findAll();

        List<Table> tableList = tableRepository.findByFloorAndInProgressOrders(0);
        assertEquals(7, tableList.size());
        for (Table table : tableList){
            assertEquals(1, table.getOrders().size());
            for (Order order : table.getOrders()){
                assertNotEquals(order.getStatus(), OrderStatus.FINISHED);
            }
        }
        tableList.sort(Comparator.comparing(Table::getId));
        assertEquals(1, tableList.get(0).getId());
        assertEquals(2, tableList.get(1).getId());
        assertEquals(3, tableList.get(2).getId());
        assertEquals(4, tableList.get(3).getId());
        assertEquals(5, tableList.get(4).getId());
        assertEquals(9, tableList.get(5).getId());
        assertEquals(10, tableList.get(6).getId());

        tableList = tableRepository.findByFloorAndInProgressOrders(1);
        assertEquals(0, tableList.size());
    }

    @Test
    public void testFindByFloorAndNoInProgressOrders(){
        List<Table> tableList = tableRepository.findByFloorAndNoInProgressOrders(0, List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L));
        assertEquals(5, tableList.size());

        tableList.sort(Comparator.comparing(Table::getId));
        assertEquals(8, tableList.get(0).getId());
        assertEquals(9, tableList.get(1).getId());
        assertEquals(10, tableList.get(2).getId());
        assertEquals(120, tableList.get(3).getId());
        assertEquals(121, tableList.get(4).getId());

        tableList = tableRepository.findByFloorAndNoInProgressOrders(1, List.of(1L));
        assertEquals(4, tableList.size());

        tableList.sort(Comparator.comparing(Table::getId));
        assertEquals(11, tableList.get(0).getId());
        assertEquals(12, tableList.get(1).getId());
        assertEquals(13, tableList.get(2).getId());
        assertEquals(14, tableList.get(3).getId());
    }

    @Test
    public void testFindTableByIdIfHasActiveOrders(){
        Optional<Table> table = tableRepository.findTableByIdIfHasActiveOrders(1L);
        assertTrue(table.isPresent());

        Table tableic = table.get();
        assertEquals(1, tableic.getOrders().size()); //ima bar jedan order
        for (Order order : tableic.getOrders()){
            assertNotEquals(order.getStatus(), OrderStatus.FINISHED); //ako ima order onda ono nije u stanju finished
        }

        table = tableRepository.findTableByIdIfHasActiveOrders(6L); //nece naci sto, jer sto sa id 6 ima samo finished
        assertFalse(table.isPresent());

        table = tableRepository.findTableByIdIfHasActiveOrders(120L); //nece naci sto, jer sto sa id 6 ima samo finished
        assertFalse(table.isPresent());  //njega ce naci, jer nema nijednu porudzbinu
    }
}
