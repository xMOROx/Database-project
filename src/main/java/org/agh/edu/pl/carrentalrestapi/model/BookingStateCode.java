package org.agh.edu.pl.carrentalrestapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "BookingStateCodes")
public class BookingStateCode implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @JsonIgnore
    private Long id;
    @JsonProperty("bookingStateCode")
    @Column(name = "bookingCode", columnDefinition = "CHAR(3) NOT NULL")
    @NotBlank(message = "Booking state code is required")
    @Size(max = 3, message = "Booking state code must be between 1 and 3 characters long")
    private String bookingCode;

    @JsonProperty("description")
    @Column(name = "description", columnDefinition = "VARCHAR(50) NOT NULL")
    @NotBlank(message = "Description is required")
    @Size(max = 50, message = "Description must be between 1 and 50 characters long")
    private String description;

    @OneToMany(mappedBy = "bookingStateCode")
    @JsonIgnore
    private List<Booking> booking;
    public BookingStateCode() {
        super();
    }

    public BookingStateCode(Long id,
                            String bookingCode,
                            String description,
                            List<Booking> booking) {
        super();
        this.id = id;
        this.bookingCode = bookingCode;
        this.description = description;
        this.booking = booking;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookingCode() {
        return bookingCode;
    }

    public void setBookingCode(String bookingCode) {
        this.bookingCode = bookingCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Booking> getBooking() {
        return booking;
    }

    @Override
    public String toString() {
        return "BookingStateCode{" +
                "id=" + id +
                ", bookingCode='" + bookingCode + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
