package org.agh.edu.pl.carrentalrestapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.io.Serializable;

@Entity
@Table(name = "Vehicle_Parameters")
@JsonNaming(value = PropertyNamingStrategies.UpperCamelCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class VehicleParameters implements Serializable {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "ID")
    @JsonIgnore
    private Long id;

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
    @Min(value = 3, message = "Doors number must be between 1 and 5")
    @Max(value = 5, message = "Doors number must be between 1 and 5")
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
    @Column(name = "Description", columnDefinition = "TEXT")
    @NotBlank(message = "Description is required")
    private String description;

    @JsonProperty()
    @Column(name = "PhotoURL", columnDefinition = "VARCHAR(255) NULL")
    private String photoURL;

    @OneToOne( cascade = {CascadeType.PERSIST}, optional = true)
    @JoinColumn(name = "vehicleID", referencedColumnName = "ID", nullable = true)
    @JsonIgnore
    private Vehicle vehicle;

    public VehicleParameters() {
        super();
    }

    public VehicleParameters(Long vehicleId,
                             String bodyType,
                             Integer productionYear,
                             String fullType,
                             Integer power,
                             String gearbox,
                             Boolean frontWheelDrive,
                             Integer doorsNumber,
                             Integer seatsNumber,
                             String color,
                             Boolean metalic,
                             String description,
                             String photoURL,
                             Vehicle vehicle) {
        this.id = vehicleId;
        this.bodyType = bodyType;
        this.productionYear = productionYear;
        this.fuelType = fullType;
        this.power = power;
        this.gearbox = gearbox;
        this.frontWheelDrive = frontWheelDrive;
        this.doorsNumber = doorsNumber;
        this.seatsNumber = seatsNumber;
        this.color = color;
        this.metalic = metalic;
        this.description = description;
        this.photoURL = photoURL;
        this.vehicle = vehicle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long vehicleId) {
        this.id = vehicleId;
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

    public void setFuelType(String fullType) {
        this.fuelType = fullType;
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

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public String toString() {
        return "VehicleParameters{" +
                "vehicleId=" + id +
                ", bodyType='" + bodyType + '\'' +
                ", productionYear=" + productionYear +
                ", fullType='" + fuelType + '\'' +
                ", power=" + power +
                ", gearbox='" + gearbox + '\'' +
                ", frontWheelDrive=" + frontWheelDrive +
                ", doorsNumber=" + doorsNumber +
                ", seatsNumber=" + seatsNumber +
                ", color='" + color + '\'' +
                ", metalic=" + metalic +
                ", description='" + description + '\'' +
                ", photoURL='" + photoURL + '\'' +
                ", vehicleID=" + vehicle.getId() +
                '}';
    }
}
