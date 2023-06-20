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
import jakarta.validation.constraints.*;

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
    @Size(min = 6, max = 10, message = "Registration must be between 6 and 10 characters long")
    @Pattern(regexp = "^[A-Z][A-Z0-9]+$", message = "Registration must contain only uppercase letters and digits. For example: 'KR1234'")
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

    @JsonProperty()
    @Column(name = "Body_Type", columnDefinition = "VARCHAR(100) NOT NULL")
    @NotBlank(message = "Body type is required")
    @Size(min = 1, max = 100, message = "Body type must be between 1 and 100 characters long")
    private String bodyType;

    @JsonProperty()
    @Column(name = "Production_Year", columnDefinition = "INT NOT NULL")
    @Min(value = 1900, message = "Production year must be greater than 1900")
    private Integer productionYear;

    @JsonProperty()
    @Column(name = "Fuel_Type", columnDefinition = "VARCHAR(30) NOT NULL")
    @NotBlank(message = "Fuel type is required")
    @Size(min = 1, max = 30, message = "Fuel type must be between 1 and 30 characters long")
    private String fuelType;

    @JsonProperty()
    @Column(name = "Power", columnDefinition = "INT NOT NULL")
    @Positive(message = "Power must be greater than 0")
    private Integer power;

    @JsonProperty()
    @Column(name = "Gearbox", columnDefinition = "VARCHAR(50) NOT NULL")
    @NotBlank(message = "Gearbox is required")
    @Size(min = 1, max = 50, message = "Gearbox must be between 1 and 50 characters long")
    private String gearbox;

    @JsonProperty()
    @Column(name = "Front_Wheel_Drive", columnDefinition = "tinyint NOT NULL default 1")
    private Boolean frontWheelDrive;

    @JsonProperty()
    @Column(name = "Doors_Number", columnDefinition = "INT NOT NULL")
    @Min(value = 3, message = "Doors number must be between 3 and 5")
    @Max(value = 5, message = "Doors number must be between 3 and 5")
    private Integer doorsNumber;

    @JsonProperty()
    @Column(name = "Seats_Number", columnDefinition = "INT NOT NULL default 5")
    @Min(value = 2, message = "Seats number must be between 2 and 9")
    @Max(value = 9, message = "Seats number must be between 2 and 9")
    private Integer seatsNumber;

    @JsonProperty()
    @Column(name = "Color", columnDefinition = "VARCHAR(50) NOT NULL")
    @NotBlank(message = "Color is required")
    @Size(min = 1, max = 50, message = "Color must be between 1 and 50 characters long")
    private String color;

    @JsonProperty()
    @Column(name = "Metalic", columnDefinition = "tinyint NOT NULL default 0")
    private Boolean metalic;

    @JsonProperty()
    @Column(name = "Description", columnDefinition = "varchar(1000) NOT NULL")
    @NotBlank(message = "Description is required")
    private String description;

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
                   String photoURL,
                   String bodyType,
                   Integer productionYear,
                   String fuelType,
                   Integer power,
                   String gearbox,
                   Boolean frontWheelDrive,
                   Integer doorsNumber,
                   Integer seatsNumber,
                   String color,
                   Boolean metalic,
                   String description,
                   BigDecimal dailyFee,
                   Boolean bestOffer,
                   VehicleStatus vehicleStatus,
                   Location location,
                   List<Booking> bookings,
                   List<Comment> comments,
                   List<Stars> stars,
                   Set<Equipment> equipment) {

        this.id = id;
        this.registration = registration;
        this.brand = brand;
        this.model = model;
        this.photoURL = photoURL;
        this.bodyType = bodyType;
        this.productionYear = productionYear;
        this.fuelType = fuelType;
        this.power = power;
        this.gearbox = gearbox;
        this.frontWheelDrive = frontWheelDrive;
        this.doorsNumber = doorsNumber;
        this.seatsNumber = seatsNumber;
        this.color = color;
        this.metalic = metalic;
        this.description = description;
        this.dailyFee = dailyFee;
        this.bestOffer = bestOffer;
        this.vehicleStatus = vehicleStatus;
        this.location = location;
        this.bookings = bookings;
        this.comments = comments;
        this.stars = stars;
        this.equipment = equipment;
    }


    public Vehicle(String registration,
                   String brand,
                   String model,
                   String photoURL,
                   String bodyType,
                   Integer productionYear,
                   String fuelType,
                   Integer power,
                   String gearbox,
                   Boolean frontWheelDrive,
                   Integer doorsNumber,
                   Integer seatsNumber,
                   String color,
                   Boolean metalic,
                   String description,
                   BigDecimal dailyFee,
                   Boolean bestOffer,
                   VehicleStatus vehicleStatus,
                   Location location) {
        this.registration = registration;
        this.brand = brand;
        this.model = model;
        this.photoURL = photoURL;
        this.bodyType = bodyType;
        this.productionYear = productionYear;
        this.fuelType = fuelType;
        this.power = power;
        this.gearbox = gearbox;
        this.frontWheelDrive = frontWheelDrive;
        this.doorsNumber = doorsNumber;
        this.seatsNumber = seatsNumber;
        this.color = color;
        this.metalic = metalic;
        this.description = description;
        this.dailyFee = dailyFee;
        this.bestOffer = bestOffer;
        this.vehicleStatus = vehicleStatus;
        this.location = location;
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

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public Integer getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(Integer productionYear) {
        this.productionYear = productionYear;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public String getGearbox() {
        return gearbox;
    }

    public void setGearbox(String gearbox) {
        this.gearbox = gearbox;
    }

    public Boolean getFrontWheelDrive() {
        return frontWheelDrive;
    }

    public void setFrontWheelDrive(Boolean frontWheelDrive) {
        this.frontWheelDrive = frontWheelDrive;
    }

    public Integer getDoorsNumber() {
        return doorsNumber;
    }

    public void setDoorsNumber(Integer doorsNumber) {
        this.doorsNumber = doorsNumber;
    }

    public Integer getSeatsNumber() {
        return seatsNumber;
    }

    public void setSeatsNumber(Integer seatsNumber) {
        this.seatsNumber = seatsNumber;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Boolean getMetalic() {
        return metalic;
    }

    public void setMetalic(Boolean metalic) {
        this.metalic = metalic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
                ", photoURL='" + photoURL + '\'' +
                ", bodyType='" + bodyType + '\'' +
                ", productionYear=" + productionYear +
                ", fuelType='" + fuelType + '\'' +
                ", power=" + power +
                ", gearbox='" + gearbox + '\'' +
                ", frontWheelDrive=" + frontWheelDrive +
                ", doorsNumber=" + doorsNumber +
                ", seatsNumber=" + seatsNumber +
                ", color='" + color + '\'' +
                ", metalic=" + metalic +
                ", description='" + description + '\'' +
                ", dailyFee=" + dailyFee +
                ", bestOffer=" + bestOffer +
                ", vehicleStatus=" + vehicleStatus +
                ", location=" + location +
                '}';
    }
}


