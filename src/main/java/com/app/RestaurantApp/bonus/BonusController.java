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
    public ResponseEntity<List<BonusDTO>> getBonusesOfEmployee(@PathVariable String email)  {
        try {
            return new ResponseEntity<>(bonusService.getBonusesOfEmployee(email), HttpStatus.OK);
        } catch (UserException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/createBonus")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<String> createBonus(@RequestBody BonusDTO bonusDTO) {
        try {
            bonusService.createBonus(bonusDTO);
        } catch (BonusException | UserException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Unknown error happened while adding bonus!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Bonus added successfully!", HttpStatus.OK);
    }

}
