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
@Table(name = "VehicleStatuses")
public class VehicleStatus implements Serializable {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "ID")
    @JsonIgnore
    private Long id;
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

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    @Override
    public String toString() {
        return "VehicleStatus{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
