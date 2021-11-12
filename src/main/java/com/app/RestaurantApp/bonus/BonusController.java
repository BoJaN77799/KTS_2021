package com.app.RestaurantApp.bonus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/bonuses")
public class BonusController {

    @Autowired
    private BonusService bonusService;

    @GetMapping(value = "/getBonusesOfEmployee/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    private List<BonusDTO> getBonusesofEmployee(@PathVariable String email){
        return bonusService.getBonusesOfEmployee(email);
    }

}
