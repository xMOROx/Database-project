package org.agh.edu.pl.carrentalrestapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

@Entity
@Table(name = "Comments")
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "ID")
    @JsonIgnore
    private Long id;

    @JsonProperty("content")
    @Column(name = "Content", columnDefinition = "TEXT NOT NULL")
    @NotBlank(message = "Content is required")
    private String content;

    @JsonProperty("creationDate")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "CreationDate", columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP")
    @NotBlank(message = "Creation date is required")
    private String creationDate;

    @JsonProperty("modificationDate")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "modificationDate", columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP")
    @NotBlank(message = "Modification date is required")
    private String modificationDate;

    @JsonProperty("rating")
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
                   String creationDate,
                   String modificationDate,
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

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(String modificationDate) {
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