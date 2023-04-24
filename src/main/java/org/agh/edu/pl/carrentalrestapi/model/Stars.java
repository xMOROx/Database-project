package org.agh.edu.pl.carrentalrestapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

@Entity
@Table(name = "Stars")
public class Stars  implements Serializable {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "ID")
    @JsonIgnore
    private Long id;

    @JsonProperty("stars")
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
