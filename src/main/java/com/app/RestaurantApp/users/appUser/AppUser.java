package com.app.RestaurantApp.users.appUser;

import com.app.RestaurantApp.enums.UserType;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue(value="U")
@SQLDelete(sql = "UPDATE app_user " + "SET deleted = true " + "WHERE id = ?")
@Where(clause = "deleted = false")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "telephone", nullable = false)
    private String telephone;

    @Column(name = "is_password_changed", nullable = false)
    private boolean isPasswordChanged;

    @Column(name = "is_email_verified", nullable = false)
    private boolean emailVerified = false;

    @Column(name="address")
    private String address;

    @Column(name = "deleted")
    private boolean deleted = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false)
    private UserType userType;

    public AppUser() {
    }

    public AppUser (AppUser appUser){
        this.firstName = appUser.firstName;
        this.lastName = appUser.lastName;
        this.password = appUser.password;
        this.id = appUser.id;
        this.email = appUser.email;
        this.gender = appUser.gender;
        this.telephone = appUser.telephone;
        this.address = appUser.address;
        this.isPasswordChanged = appUser.isPasswordChanged;
        this.emailVerified = appUser.emailVerified;
        this.userType = appUser.userType;
        this.deleted = appUser.deleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public boolean isPasswordChanged() {
        return isPasswordChanged;
    }

    public void setPasswordChanged(boolean passwordChanged) {
        isPasswordChanged = passwordChanged;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
