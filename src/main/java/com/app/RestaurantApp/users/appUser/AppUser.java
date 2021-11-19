package com.app.RestaurantApp.users.appUser;

import com.app.RestaurantApp.enums.UserType;
import com.app.RestaurantApp.users.Authority;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue(value="U")
public class AppUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "password", nullable = false)
    protected String password;

    @Column(name = "first_name", nullable = false)
    protected String firstName;

    @Column(name = "last_name", nullable = false)
    protected String lastName;

    @Column(name = "email", unique = true, nullable = false)
    protected String email;

    @Column(name = "gender", nullable = false)
    protected String gender;

    @Column(name = "telephone", nullable = false)
    protected String telephone;

    @Column(name = "is_password_changed", nullable = false)
    protected boolean isPasswordChanged;

    @Column(name = "is_email_verified", nullable = false)
    protected boolean emailVerified = false;

    @Column(name="address")
    protected String address;

    @Column(name = "deleted")
    protected boolean deleted = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false)
    protected UserType userType;

    @Column(name="profile_photo")
    protected String profilePhoto;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority",
            joinColumns = @JoinColumn(name = "app_user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    private List<Authority> authorities;

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
        this.profilePhoto = appUser.profilePhoto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
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

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }
}
