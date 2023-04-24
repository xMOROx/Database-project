package org.agh.edu.pl.carrentalrestapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @JsonIgnore
    private Long id;

    @JsonProperty("firstName")
    @Column(name = "FirstName", columnDefinition = "varchar(70) NOT NULL")
    @NotBlank(message = "First name is required")
    @Size(min = 1, max = 70, message = "First name must be between 1 and 70 characters long")
    private String firstName;

    @JsonProperty("surName")
    @Column(name = "SurName", columnDefinition = "varchar(70) NOT NULL")
    @NotBlank(message = "Surname is required")
    @Size(min = 1, max = 70, message = "Surname must be between 1 and 70 characters long")
    private String surName;

    @JsonProperty("email")
    @Column(name = "Email", columnDefinition = "varchar(255) NOT NULL")
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Size(min = 5, max = 255, message = "Email must be between 5 and 255 characters long")
    private String email;

    @JsonProperty("login")
    @Column(name = "Login", columnDefinition = "varchar(60) NOT NULL")
    @NotBlank(message = "Login is required")
    @Size(min = 5, max = 60, message = "Login must be between 5 and 60 characters long")
    private String login;

    @Column(name = "Password", columnDefinition = "varchar(255) NOT NULL")
    @JsonProperty("password")
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters long")
    private String password;

    @Column(name = "PhoneNumber", columnDefinition = "varchar(20) NOT NULL")
    @JsonProperty("phoneNumber")
    @NotBlank(message = "Phone number is required")
    @Size(min = 9, max = 20, message = "Phone number must be between 9 and 20 characters long")
    private String phoneNumber;

    @JsonProperty("pesel")
    @Column(name = "Pesel", columnDefinition = "varchar(11) NOT NULL")
    @NotBlank(message = "Pesel is required")
    @Size(min = 11, max = 11, message = "Pesel must be 11 characters long")
    private String pesel;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private List<Booking> bookings;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private List<Comment> comments;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "AppUsersRoles", joinColumns = @JoinColumn(name = "userID"), inverseJoinColumns = @JoinColumn(name = "userRoleID"))
    @JsonIgnore
    private Set<UserRole> userRoles = new HashSet<>();

    public User() {
        super();
    }

    public User(Long id,
                String firstName,
                String surName,
                String email,
                String login,
                String password,
                String phoneNumber,
                String pesel,
                List<Booking> bookings,
                List<Comment> comments,
                Set<UserRole> userRoles) {
        this.id = id;
        this.firstName = firstName;
        this.surName = surName;
        this.email = email;
        this.login = login;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.pesel = pesel;
        this.bookings = bookings;
        this.comments = comments;
        this.userRoles = userRoles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", surName='" + surName + '\'' +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", pesel='" + pesel + '\'' +
                ", userRoles=" + userRoles +
                '}';
    }
}
