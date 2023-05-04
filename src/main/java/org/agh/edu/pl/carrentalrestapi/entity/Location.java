package org.agh.edu.pl.carrentalrestapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Locations")
public class Location  implements Serializable {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "ID")
    @JsonIgnore
    private Long id;

    @JsonProperty("country")
    @Column(name = "Country", columnDefinition = "varchar(100) not null")
    @NotBlank(message = "Country is required")
    @Size(min = 1, max = 100, message = "Country must be between 1 and 100 characters long")
    private String country;

    @JsonProperty("city")
    @Column(name = "City", columnDefinition = "varchar(100) not null")
    @NotBlank(message = "City is required")
    @Size(min = 1, max = 100, message = "City must be between 1 and 100 characters long")
    private String city;

    @JsonProperty("address")
    @Column(name = "Address", columnDefinition = "varchar(100) not null")
    @NotBlank(message = "Address is required")
    @Size(min = 1, max = 100, message = "Address must be between 1 and 100 characters long")
    private String address;

    @JsonProperty("email")
    @Column(name = "Email", columnDefinition = "varchar(255) not null")
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    @Size(min = 1, max = 255, message = "Email must be between 1 and 255 characters long")
    private String email;

    @JsonProperty("phoneNumber")
    @Column(name = "PhoneNumber", columnDefinition = "varchar(20) not null")
    @NotBlank(message = "Phone number is required")
    @Size(min = 9, max = 20, message = "Phone number must be between 9 and 20 characters long")
    private String phoneNumber;

    @JsonProperty("openHours")
    @Column(name = "OpeningHours", columnDefinition = "varchar(20) not null")
    @NotBlank(message = "Opening hours is required")
    @Size(min = 1, max = 20, message = "Opening hours must be between 1 and 20 characters long")
    private String openingHours;
    @JsonProperty("closingHours")
    @Column(name = "ClosingHours", columnDefinition = "varchar(20) not null")
    @NotBlank(message = "Closing hours is required")
    @Size(min = 1, max = 20, message = "Closing hours must be between 1 and 20 characters long")
    private String closingHours;
    @JsonProperty("PostalCode")
    @Column(name = "PostalCode", columnDefinition = "varchar(15) not null")
    @NotBlank(message = "Postal code is required")
    @Size(min = 1, max = 15, message = "Postal code must be between 1 and 15 characters long")
    private String postalCode;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private List<Booking> bookings;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private List<Vehicle> vehicles;

    public Location() {
        super();
    }

    public Location(Long id,
                    String country,
                    String city,
                    String address,
                    String email,
                    String phoneNumber,
                    String openingHours,
                    String closingHours,
                    String postalCode,
                    List<Booking> bookings,
                    List<Vehicle> vehicles) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.openingHours = openingHours;
        this.closingHours = closingHours;
        this.postalCode = postalCode;
        this.bookings = bookings;
        this.vehicles = vehicles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openHours) {
        this.openingHours = openHours;
    }

    public String getClosingHours() {
        return closingHours;
    }

    public void setClosingHours(String closeHours) {
        this.closingHours = closeHours;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", openingHours='" + openingHours + '\'' +
                ", closingHours='" + closingHours + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
}


