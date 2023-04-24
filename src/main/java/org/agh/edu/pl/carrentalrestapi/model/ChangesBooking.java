package org.agh.edu.pl.carrentalrestapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

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
    @Column(name = "changeDate", columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP NOT NULL")
    @NotBlank(message = "Change date is required")
    private String changeDate;
    @JsonProperty("who")
    @Column(name = "who", columnDefinition = "VARCHAR(100) NOT NULL")
    @NotBlank(message = "Who is required")
    @Size(min = 1, max = 100, message = "Who must be between 1 and 100 characters")
    private String who;

    public ChangesBooking() {
        super();
    }

    public ChangesBooking(Long id,
                          Booking booking,
                          String who,
                          String changeDate) {
        super();
        this.id = id;
        this.booking = booking;
        this.who = who;
        this.changeDate = changeDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(String changeDate) {
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
                ", who='" + who + '\'' +
                ", changeDate='" + changeDate + '\'' +
                '}';
    }
}


