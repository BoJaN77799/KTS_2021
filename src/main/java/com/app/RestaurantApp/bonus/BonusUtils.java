package com.app.RestaurantApp.bonus;


public class BonusUtils {

    public static void CheckBonusInfo(Bonus bonus) throws BonusException {
        if (bonus == null)
            throw new BonusException("Invalid bonus sent from front!");
        else if (bonus.getDate() == null || bonus.getEmployee() == null)
            throw new BonusException("Invalid bonus data sent from front! Null values in attributes!");

        if (bonus.getAmount() <= 0)
            throw new BonusException("Bonus can not be negative number or zero!");
    }

}
