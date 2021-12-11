package com.app.RestaurantApp.reports;

import com.app.RestaurantApp.enums.UserType;
import com.app.RestaurantApp.reports.dto.UserReportDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.app.RestaurantApp.reports.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportsServiceIntegrationTests {

    @Autowired
    private ReportsService reportsService;

    @Test
    public void testActivityReport() {
        // Test invoke
        List<UserReportDTO> activityReport = reportsService.activityReport(DATE_FROM, DATE_TO);

        // Verifying
        assertNotNull(activityReport);
        assertEquals(3, activityReport.size());
        assertEquals( 6, activityReport.stream().filter(user -> user.getUserType() == UserType.WAITER).findAny().get().getOrdersAccomplished());
        assertEquals( 3, activityReport.stream().filter(user -> user.getUserType() == UserType.COOK).findAny().get().getOrdersAccomplished());
        assertEquals( 3, activityReport.stream().filter(user -> user.getUserType() == UserType.BARMAN).findAny().get().getOrdersAccomplished());
    }

}
