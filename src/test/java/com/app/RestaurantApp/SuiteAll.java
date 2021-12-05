package com.app.RestaurantApp;

import com.app.RestaurantApp.food.FoodRepositoryIntegrationTests;
import com.app.RestaurantApp.order.OrderRepositoryIntegrationTests;
import com.app.RestaurantApp.users.appUser.AppUserServiceIntegrationTests;
import com.app.RestaurantApp.users.appUser.AppUserServiceUnitTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({AppUserServiceUnitTests.class, AppUserServiceIntegrationTests.class})
public class SuiteAll {


}