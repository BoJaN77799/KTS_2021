package com.app.RestaurantApp.users.appUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    @Query("SELECT u FROM AppUser u WHERE NOT u.id= ?1")
    List<AppUser> findAllUsersButAdmin(Long adminId);

    @Query("SELECT u FROM AppUser u " +
            "WHERE (:search = '' " +
            "or lower(u.firstName) like lower(concat('%', :search, '%')) " +
            "or lower(u.lastName) like lower(concat('%', :search, '%'))" +
            "or lower(u.email) like lower(concat('%', :search, '%')))" +
            "AND (:userType = '' or u.userType = :userType)")
    List<AppUser> searchUsersAdmin(@Param("search") String searchField, @Param("userType") String userType, Pageable pageable);

    Optional<AppUser> findByEmail(String email);

}
