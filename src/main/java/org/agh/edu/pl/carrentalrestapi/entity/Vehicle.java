package org.agh.edu.pl.carrentalrestapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
@JsonNaming(value = PropertyNamingStrategies.UpperCamelCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Vehicle implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @JsonIgnore
    private Long id;

    @JsonProperty
    @Column(name = "Registration", columnDefinition = "VARCHAR(30) NOT NULL UNIQUE")
    @NotBlank(message = "Registration is required")
    @Size(min = 1, max = 30, message = "Registration must be between 1 and 30 characters long")
    private String registration;

    @JsonProperty
    @Column(name = "Brand", columnDefinition = "VARCHAR(255) NULL")
    @Size(min = 1, max = 255, message = "Brand must be between 1 and 255 characters long")
    private String brand;

    @JsonProperty
    @Column(name = "Model", columnDefinition = "VARCHAR(255) NULL")
    @Size(min = 1, max = 255, message = "Model must be between 1 and 255 characters long")
    private String model;

    @JsonProperty()
    @Column(name = "PhotoURL", columnDefinition = "VARCHAR(255) NULL")
    private String photoURL;

    @JsonProperty
    @Column(name = "Daily_Fee", columnDefinition = "DECIMAL(15,2) NOT NULL")
    @Positive(message = "Daily fee must be positive")
    private BigDecimal dailyFee;
    @JsonProperty
    @Column(name = "Best_Offer", columnDefinition = "tinyint NOT NULL default 0")
    private Boolean bestOffer;
    @ManyToOne
    @JoinColumn(name = "vehicle_StatusID")
    @JsonIgnore
    private VehicleStatus vehicleStatus;
    @ManyToOne
    @JoinColumn(name = "locationID")
    @JsonIgnore
    private Location location;
    @OneToOne(mappedBy = "vehicle", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JsonIgnore
    private VehicleParameters vehicleParameters;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Booking> bookings;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Comment> comments;
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Stars> stars;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "Equipment_Set",
            joinColumns = {@JoinColumn(name = "vehicleID")},
            inverseJoinColumns = {@JoinColumn(name = "equipmentID")}
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
                   Boolean bestOffer,
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
        this.bestOffer = bestOffer;
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

    @JsonSetter
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

    public void setDailyFee(BigDecimal dailyFee) {
        this.dailyFee = dailyFee;
    }

    public VehicleStatus getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleStatus(VehicleStatus vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public VehicleParameters getVehicleParameters() {
        return vehicleParameters;
    }

    public void setVehicleParameters(VehicleParameters vehicleParameters) {
        this.vehicleParameters = vehicleParameters;
    }

    public List<Stars> getStars() {
        return stars;
    }

    public void setStars(List<Stars> stars) {
        this.stars = stars;
    }

    public Set<Equipment> getEquipment() {
        return equipment;
    }

    public void setEquipment(Set<Equipment> equipment) {
        this.equipment = equipment;
    }

    public Boolean getBestOffer() {
        return bestOffer;
    }

    public void setBestOffer(Boolean bestOffer) {
        this.bestOffer = bestOffer;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", registration='" + registration + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", dailyFee=" + dailyFee +
                ", bestOffer=" + bestOffer +
                ", vehicleStatusId=" + vehicleStatus.getId() +
                ", locationId=" + location.getId() +
                ", vehicleParametersId=" + vehicleParameters.getId() +
                '}';
    }
}


