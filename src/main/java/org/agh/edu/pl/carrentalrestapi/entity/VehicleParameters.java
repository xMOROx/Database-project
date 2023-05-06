package org.agh.edu.pl.carrentalrestapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

@Entity
@Table(name = "Vehicle_Parameters")
@JsonNaming(value = PropertyNamingStrategies.UpperCamelCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class VehicleParameters implements Serializable {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "VehicleID")
    @JsonIgnore
    private Long id;

    @JsonProperty("bodyType")
    @Column(name = "Body_Type", columnDefinition = "VARCHAR(100) NOT NULL")
    @NotBlank(message = "Body type is required")
    @Size(min = 1, max = 100, message = "Body type must be between 1 and 100 characters long")
    private String bodyType;

    @JsonProperty("productionYear")
    @Column(name = "Production_Year", columnDefinition = "INT NOT NULL")
    @NotBlank(message = "Production year is required")
    private Integer productionYear;

    @JsonProperty("fuelType")
    @Column(name = "Fuel_Type", columnDefinition = "VARCHAR(30) NOT NULL")
    @NotBlank(message = "Fuel type is required")
    @Size(min = 1, max = 30, message = "Fuel type must be between 1 and 30 characters long")
    private String fuelType;

    @JsonProperty("power")
    @Column(name = "Power", columnDefinition = "INT NOT NULL")
    @NotBlank(message = "Power is required")
    private Integer power;

    @JsonProperty("gearbox")
    @Column(name = "Gearbox", columnDefinition = "VARCHAR(50) NOT NULL")
    @NotBlank(message = "Gearbox is required")
    @Size(min = 1, max = 50, message = "Gearbox must be between 1 and 50 characters long")
    private String gearbox;

    @JsonProperty("frontWheelDrive")
    @Column(name = "Front_Wheel_Drive", columnDefinition = "tinyint NOT NULL default 1")
    private Boolean frontWheelDrive;

    @JsonProperty("doorsNumber")
    @Column(name = "Doors_Number", columnDefinition = "INT NOT NULL")
    @NotBlank(message = "Doors number is required")
    private Integer doorsNumber;

    @JsonProperty("seatsNumber")
    @Column(name = "Seats_Number", columnDefinition = "INT NOT NULL default 5")
    private Integer seatsNumber;

    @JsonProperty("color")
    @Column(name = "Color", columnDefinition = "VARCHAR(50) NOT NULL")
    @NotBlank(message = "Color is required")
    @Size(min = 1, max = 50, message = "Color must be between 1 and 50 characters long")
    private String color;

    @JsonProperty("metalic")
    @Column(name = "Metalic", columnDefinition = "tinyint NOT NULL default 0")
    private Boolean metalic;

    @JsonProperty("description")
    @Column(name = "Description", columnDefinition = "TEXT")
    @NotBlank(message = "Description is required")
    private String description;

    @JsonProperty("photoURL")
    @Column(name = "PhotoURL", columnDefinition = "VARCHAR(255) NULL")
    private String photoURL;

    @OneToOne
    @JoinColumn(name = "vehicleID")
    @MapsId
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
