package org.agh.edu.pl.carrentalrestapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Vehicles")
public class Vehicle implements Serializable {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "ID")
    @JsonIgnore
    private Long id;

    @JsonProperty("registration")
    @Column(name = "Registration", columnDefinition = "VARCHAR(30) NOT NULL")
    @NotBlank(message = "Registration is required")
    @Size(min = 1, max = 30, message = "Registration must be between 1 and 30 characters long")
    private String registration;

    @JsonProperty("brand")
    @Column(name = "Brand", columnDefinition = "VARCHAR(255) NULL")
    @Size(min = 1, max = 255, message = "Brand must be between 1 and 255 characters long")
    private String brand;

    @JsonProperty("model")
    @Column(name = "Model", columnDefinition = "VARCHAR(255) NULL")
    @Size(min = 1, max = 255, message = "Model must be between 1 and 255 characters long")
    private String model;

    @JsonProperty("dailyFee")
    @Column(name = "DailyFee", columnDefinition = "DECIMAL(15,2) NOT NULL")
    @NotBlank(message = "Daily fee is required")
    @Positive(message = "Daily fee must be positive")
    private BigDecimal dailyFee;
    @ManyToOne
    @JoinColumn(name = "vehicleStatusID")
    @JsonIgnore
    private VehicleStatus vehicleStatus;

    @ManyToOne
    @JoinColumn(name = "locationID")
    @JsonIgnore
    private Location location;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private List<Booking> bookings;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private List<Comment> comments;
    @OneToOne(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private VehicleParameters vehicleParameters;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private List<Stars> stars;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
        name = "EquipmentSet",
        joinColumns = { @JoinColumn(name = "vehicleID") },
        inverseJoinColumns = { @JoinColumn(name = "equipmentID") }
    )
    @JsonIgnore
    private Set<Equipment> equipment = new HashSet<>();
    public Vehicle() {
        super();
    }

    public Vehicle(Long id,
                   String registration,
                   String brand,
                   String model,
                   BigDecimal dailyFee,
                   VehicleStatus vehicleStatus,
                   Location location,
                   VehicleParameters vehicleParameters,
                   List<Booking> bookings,
                   List<Comment> comments,
                   List<Stars> stars,
                   Set<Equipment> equipment) {
        this.id = id;
        this.registration = registration;
        this.brand = brand;
        this.model = model;
        this.dailyFee = dailyFee;
        this.vehicleStatus = vehicleStatus;
        this.location = location;
        this.bookings = bookings;
        this.comments = comments;
        this.vehicleParameters = vehicleParameters;
        this.stars = stars;
        this.equipment = equipment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public BigDecimal getDailyFee() {
        return dailyFee;
    }

    public VehicleStatus getVehicleStatus() {
        return vehicleStatus;
    }

    public Location getLocation() {
        return location;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public VehicleParameters getVehicleParameters() {
        return vehicleParameters;
    }

    public List<Stars> getStars() {
        return stars;
    }

    public Set<Equipment> getEquipment() {
        return equipment;
    }

    public void setDailyFee(BigDecimal dailyFee) {
        this.dailyFee = dailyFee;
    }
}

