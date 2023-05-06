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
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Equipments")
@JsonNaming(value = PropertyNamingStrategies.UpperCamelCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Equipment implements Serializable {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "ID")
    @JsonIgnore
    private Long id;
    @JsonProperty("equipmentCode")
    @Column(name = "Equipment_Code", columnDefinition = "VARCHAR(3) NOT NULL UNIQUE")
    @NotBlank(message = "Equipment code is mandatory")
    @Size(min = 1, max = 3, message = "Equipment code must be between 1 and 3 characters long")
    private String equipmentCode;

    @JsonProperty("Description")
    @Column(name = "Description", columnDefinition = "VARCHAR(50) NOT NULL")
    @NotBlank(message = "Description is mandatory")
    @Size(min = 1, max = 50, message = "Description must be between 1 and 50 characters long")
    private String description;

    @ManyToMany(mappedBy = "equipment")
    @JsonIgnore
    private Set<Vehicle> vehicles = new HashSet<>();

    public Equipment() {
        super();
    }

    public Equipment(Long id,
                     String equipmentCode,
                        String description,
                     Set<Vehicle> vehicles) {
        this.id = id;
        this.equipmentCode = equipmentCode;
        this.description = description;
        this.vehicles = vehicles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEquipmentCode() {
        return equipmentCode;
    }

    public void setEquipmentCode(String equipmentCode) {
        this.equipmentCode = equipmentCode;
    }

    public Set<Vehicle> getVehicles() {
        return vehicles;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "id=" + id +
                ", equipmentCode='" + equipmentCode + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
