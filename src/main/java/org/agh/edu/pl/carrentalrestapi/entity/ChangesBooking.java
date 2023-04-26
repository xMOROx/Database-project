package org.agh.edu.pl.carrentalrestapi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "ChangesBookings")
public class ChangesBooking implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @JsonIgnore
    private Long id;
    @ManyToOne
    @JoinColumn(name = "bookingID")
    @JsonIgnore
    private Booking booking;
    @JsonProperty("changeDate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "ChangeDate", columnDefinition = "DATETIME default CURRENT_TIMESTAMP NOT NULL")
    @NotBlank(message = "Change date is required")
    private LocalDateTime changeDate;
    @JsonProperty("who")
    @Column(name = "WhoChange", columnDefinition = "VARCHAR(100) NOT NULL")
    @NotBlank(message = "Who is required")
    @Size(min = 1, max = 100, message = "Who must be between 1 and 100 characters")
    private String whoChange;

    public ChangesBooking() {
        super();
    }

    public ChangesBooking(Long id,
                          Booking booking,
                          String who,
                          LocalDateTime changeDate) {
        super();
        this.id = id;
        this.booking = booking;
        this.whoChange = who;
        this.changeDate = changeDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWhoChange() {
        return whoChange;
    }

    public void setWhoChange(String who) {
        this.whoChange = who;
    }

    public LocalDateTime getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(LocalDateTime changeDate) {
        this.changeDate = changeDate;
    }

    public Booking getBooking() {
        return booking;
    }

    @Override
    public String toString() {
        return "ChangesBooking{" +
                "id=" + id +
                ", bookingID=" + booking.getId() +
                ", whoChange='" + whoChange + '\'' +
                ", changeDate='" + changeDate + '\'' +
                '}';
    }
}


