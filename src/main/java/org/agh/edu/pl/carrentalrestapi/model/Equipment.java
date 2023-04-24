package org.agh.edu.pl.carrentalrestapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Equipment")
public class Equipment implements Serializable {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "ID")
    @JsonIgnore
    private Long id;
    @JsonProperty("equipmentCode")
    @Column(name = "EquipmentCode")
    @NotBlank(message = "Equipment code is mandatory")
    @Size(min = 1, max = 3, message = "Equipment code must be between 1 and 3 characters long")
    private String equipmentCode;

    @ManyToMany(mappedBy = "equipment")
    @JsonIgnore
    private Set<Vehicle> vehicles = new HashSet<>();

    public Equipment() {
        super();
    }

    public Equipment(Long id,
                     String equipmentCode,
                     Set<Vehicle> vehicles) {
        this.id = id;
        this.equipmentCode = equipmentCode;
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

    @Override
    public String toString() {
        return "Equipment{" +
                "id=" + id +
                ", equipmentCode='" + equipmentCode + '\'' +
                '}';
    }
}
