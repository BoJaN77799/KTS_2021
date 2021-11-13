package com.app.RestaurantApp.users.appUser;

import com.app.RestaurantApp.enums.UserType;
import com.app.RestaurantApp.users.UserException;
import com.app.RestaurantApp.users.UserUtils;
import com.app.RestaurantApp.users.employee.Employee;
import com.app.RestaurantApp.users.employee.EmployeeService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Service
public class AppUserServiceImpl implements AppUserService{

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private EmployeeService employeeService;

    @Override
    public List<AppUser> getAllUsersButAdmin(Long adminID) {
        return appUserRepository.findAllUsersButAdmin(adminID);
    }

    @Override
    public List<AppUser> searchUsersAdmin(String searchField, String userType, Pageable pageable){
        if (searchField == null)
            searchField = "";

        if (userType == null){
            searchField = "";
        }else if (userType.equalsIgnoreCase("all")){
            searchField = "";
        }
        return appUserRepository.searchUsersAdmin(searchField, userType, pageable);
    }

    @Override
    public void createUser(AppUser user) throws UserException{
        UserUtils.CheckUserInfo(user);

        Optional<AppUser> userOptional = appUserRepository.findByEmail(user.getEmail());
        if (userOptional.isPresent()) {
            throw new UserException("An account with this email already exists");
        }

        String pw = UserUtils.generatePassword(10);

        user.setPassword(pw);
        user.setEmailVerified(false);
        user.setPasswordChanged(false);

        if (user.getUserType() == UserType.BARMAN || user.getUserType() == UserType.COOK ||
                user.getUserType() == UserType.WAITER || user.getUserType() == UserType.HEAD_COOK)
        {
            employeeService.createEmployee(new Employee(user));
        }else{
            appUserRepository.save(user);
        }

    }
}
