package org.agh.edu.pl.carrentalrestapi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Comments")
@JsonNaming(value = PropertyNamingStrategies.UpperCamelCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "ID")
    @JsonIgnore
    private Long id;

    @JsonProperty()
    @Column(name = "Content", columnDefinition = "TEXT NOT NULL")
    @NotBlank(message = "Content is required")
    private String content;

    @JsonProperty()
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "Creation_Date", columnDefinition = "DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP")
    @PastOrPresent(message = "Creation date must be in the past or in the present")
    private LocalDateTime creationDate;

    @JsonProperty()
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "Modification_Date", columnDefinition = "DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP")
    @FutureOrPresent(message = "Modification date must be in the past or in the present")
    private LocalDateTime modificationDate;

    @JsonProperty
    @Column(name = "Rating", columnDefinition = "INT NOT NULL DEFAULT 0")
    @Min(value = 1, message = "Rating must be between 1 and 10")
    @Max(value = 10, message = "Rating must be between 1 and 10")
    private int rating;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userID")
    private User user;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicleID")
    private Vehicle vehicle;

    public Comment() {
        super();
    }

    public Comment(Long id,
                   String content,
                   LocalDateTime creationDate,
                   LocalDateTime modificationDate,
                   int rating,
                   User user,
                   Vehicle vehicle) {
        super();
        this.id = id;
        this.content = content;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
        this.rating = rating;
        this.user = user;
        this.vehicle = vehicle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(LocalDateTime modificationDate) {
        this.modificationDate = modificationDate;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public User getUser() {
        return user;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", modificationDate='" + modificationDate + '\'' +
                ", rating=" + rating +
                ", userID=" + user.getId() +
                ", vehicleID=" + vehicle.getId() +
                '}';
    }
}
