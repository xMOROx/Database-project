package org.agh.edu.pl.carrentalrestapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "Bookings")
public class Booking implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @JsonIgnore
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userID")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "vehicleID")
    @JsonIgnore
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "locationID")
    @JsonIgnore
    private Location location;

    @ManyToOne
    @JoinColumn(name = "bookingStateCodeID")
    @JsonIgnore
    private BookingStateCode bookingStateCode;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ChangesBooking> changesBooking;

    @JsonProperty("receiptDate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "receiptDate", columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP NOT NULL")
    @PastOrPresent(message = "Receipt date must be in the past or present")
    private Timestamp receiptDate;

    @JsonProperty("returnDate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "returnDate", columnDefinition = "TIMESTAMP NOT NULL")
    @NotBlank(message = "Return date is required")
    @Future(message = "Return date must be in the future")
    private Timestamp returnDate;

    @JsonProperty("totalCost")
    @Column(name = "totalCost", columnDefinition = "DECIMAL(15,2) NOT NULL")
    @NotBlank
    @Positive
    private BigDecimal totalCost;

    public Booking() {
        super();
    }

    public Booking(Long id,
                   User user,
                   Vehicle vehicle,
                   Location location,
                   BookingStateCode bookingStateCode,
                   List<ChangesBooking> changesBooking,
                   Timestamp receiptDate,
                   Timestamp returnDate,
                   BigDecimal totalCost) {
        super();
        this.id = id;
        this.user = user;
        this.vehicle = vehicle;
        this.location = location;
        this.bookingStateCode = bookingStateCode;
        this.changesBooking = changesBooking;
        this.receiptDate = receiptDate;
        this.returnDate = returnDate;
        this.totalCost = totalCost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(Timestamp receiptDate) {
        this.receiptDate = receiptDate;
    }

    public Timestamp getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Timestamp returnDate) {
        this.returnDate = returnDate;
    }



    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public User getUser() {
        return user;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Location getLocation() {
        return location;
    }

    public BookingStateCode getBookingStateCode() {
        return bookingStateCode;
    }

    public List<ChangesBooking> getChangesBooking() {
        return changesBooking;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", userID=" + user.getId() +
                ", vehicleID=" + vehicle.getId() +
                ", locationID=" + location.getId() +
                ", bookingStateCodeID=" + bookingStateCode.getId() +
                ", receiptDate=" + receiptDate +
                ", returnDate=" + returnDate +
                ", totalCost=" + totalCost +
                '}';
    }
}
