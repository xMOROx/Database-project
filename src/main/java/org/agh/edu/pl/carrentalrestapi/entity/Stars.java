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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.io.Serializable;

@Entity
@Table(name = "Stars")
@JsonNaming(value = PropertyNamingStrategies.UpperCamelCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Stars  implements Serializable {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "ID")
    @JsonIgnore
    private Long id;

    @JsonProperty
    @Column(name = "stars", columnDefinition = "INTEGER NOT NULL")
    @Min(value = 1, message = "Stars must be between 1 and 5")
    @Max(value = 5, message = "Stars must be between 1 and 5")
    private Integer stars;

    @ManyToOne
    @JoinColumn(name = "vehicleID")
    @JsonIgnore
    private Vehicle vehicle;

    public Stars() {
        super();
    }

    public Stars(Long id,
                 Integer stars,
                 Vehicle vehicle) {
        super();
        this.id = id;
        this.stars = stars;
        this.vehicle = vehicle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    @Override
    public String toString() {
        return "Stars{" +
                "id=" + id +
                ", stars=" + stars +
                ", vehicleID=" + vehicle.getId() +
                '}';
    }
}
