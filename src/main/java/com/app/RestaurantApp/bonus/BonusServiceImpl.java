package com.app.RestaurantApp.bonus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BonusServiceImpl implements BonusService {

    @Autowired
    private BonusRepository bonusRepository;

}
