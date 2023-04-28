package org.agh.edu.pl.carrentalrestapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "VehicleStatus")
public class VehicleStatus implements Serializable {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "ID")
    @JsonIgnore
    private Long id;

    @JsonProperty("Type")
    @Column(name = "Type", columnDefinition = "VARCHAR(3) NOT NULL")
    @NotBlank(message = "Type is mandatory")
    @Size(min = 3, max = 3, message = "Type must be 3 characters long")
    private String type;

    @JsonProperty("Description")
    @Column(name = "Description", columnDefinition = "VARCHAR(50) NOT NULL")
    @NotBlank(message = "Description is mandatory")
    @Size(min = 3, max = 50, message = "Description must be between 3 and 50 characters long")
    private String description;

    @OneToMany(mappedBy = "vehicleStatus")
    @JsonIgnore
    private List<Vehicle> vehicles;


    public VehicleStatus() {
        super();
    }

    public VehicleStatus(Long id,
                         String description,
                         List<Vehicle> vehicles) {
        super();
        this.id = id;
        this.description = description;
        this.vehicles = vehicles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    @Override
    public String toString() {
        return "VehicleStatus{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
