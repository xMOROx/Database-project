package org.agh.edu.pl.carrentalrestapi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Bookings")
@JsonNaming(value = PropertyNamingStrategies.UpperCamelCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
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
    @JoinColumn(name = "booking_State_CodeID")
    @JsonIgnore
    private BookingStateCode bookingStateCode;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ChangesBooking> changesBooking;

    @JsonProperty()
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "receipt_Date", columnDefinition = "DATETIME default CURRENT_TIMESTAMP NULL")
    @FutureOrPresent(message = "Receipt date must be in the present or future")
    private LocalDateTime receiptDate;

    @JsonProperty()
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "return_Date", columnDefinition = "DATETIME NOT NULL")
    @Future(message = "Return date must be in the future")
    private LocalDateTime returnDate;

    @JsonProperty()
    @Column(name = "total_Cost", columnDefinition = "DECIMAL(15,2) NOT NULL")
    @Positive(message = "Total cost must be positive")
    private BigDecimal totalCost;

    public Booking() {
        super();
    }

    public Booking(
                   User user,
                   Vehicle vehicle,
                   Location location,
                   BookingStateCode bookingStateCode,
                   LocalDateTime receiptDate,
                   LocalDateTime returnDate,
                   BigDecimal totalCost) {
        super();
        this.user = user;
        this.vehicle = vehicle;
        this.location = location;
        this.bookingStateCode = bookingStateCode;
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

    public LocalDateTime getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(LocalDateTime receiptDate) {
        this.receiptDate = receiptDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
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

    public void setUser(User user) {
        this.user = user;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setBookingStateCode(BookingStateCode bookingStateCode) {
        this.bookingStateCode = bookingStateCode;
    }

    public void setChangesBooking(List<ChangesBooking> changesBooking) {
        this.changesBooking = changesBooking;
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
