package com.app.RestaurantApp.bonus;

import com.app.RestaurantApp.bonus.dto.BonusDTO;
import com.app.RestaurantApp.users.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/bonuses")
public class BonusController {

    @Autowired
    private BonusService bonusService;

    @GetMapping(value = "/getBonusesOfEmployee/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('MANAGER')")
    public List<BonusDTO> getBonusesofEmployee(@PathVariable String email) throws UserException {
        return bonusService.getBonusesOfEmployee(email);
    }

    @PostMapping(value = "/createBonus")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<BonusDTO> createBonus(@RequestBody BonusDTO bonusDTO) throws UserException {
        return new ResponseEntity<>(bonusService.createBonus(bonusDTO), HttpStatus.OK);
    }

}
