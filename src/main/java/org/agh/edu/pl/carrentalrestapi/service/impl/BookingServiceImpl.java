package org.agh.edu.pl.carrentalrestapi.service.impl;

import org.agh.edu.pl.carrentalrestapi.entity.Booking;
import org.agh.edu.pl.carrentalrestapi.entity.Vehicle;
import org.agh.edu.pl.carrentalrestapi.exception.types.BookingNotFoundException;
import org.agh.edu.pl.carrentalrestapi.exception.types.BookingUnavailableVehicleException;
import org.agh.edu.pl.carrentalrestapi.exception.types.UserNotFoundException;
import org.agh.edu.pl.carrentalrestapi.exception.types.VehicleNotFoundException;
import org.agh.edu.pl.carrentalrestapi.repository.BookingRepository;
import org.agh.edu.pl.carrentalrestapi.repository.UserRepository;
import org.agh.edu.pl.carrentalrestapi.service.BookingService;
import org.agh.edu.pl.carrentalrestapi.service.VehicleService;
import org.agh.edu.pl.carrentalrestapi.utils.enums.BookingStateCodeConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.time.temporal.ChronoUnit.DAYS;

@Service("bookingService")
@Transactional
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final VehicleService vehicleService;

    public BookingServiceImpl(BookingRepository bookingRepository,
                              UserRepository userRepository, VehicleService vehicleService) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.vehicleService = vehicleService;
    }

    @Override
    public Long addBooking(Booking booking) throws BookingUnavailableVehicleException {
        return bookingRepository.addBooking(booking);
    }

    @Override
    public void cancelBooking(Long bookingId) throws BookingNotFoundException {
        checkIfBookingExists(bookingId);
        bookingRepository.cancelBooking(bookingId);
    }

    @Override
    public void rentBooking(Long bookingId) throws BookingNotFoundException {
        checkIfBookingExists(bookingId);
        bookingRepository.bookingRent(bookingId);
    }

    @Override
    public void returnBooking(Long bookingId) throws BookingNotFoundException {
        checkIfBookingExists(bookingId);
        bookingRepository.bookingReturn(bookingId);
    }

    @Override
    public Page<Booking> getBookings(Pageable pageable) {
        return bookingRepository.findAll(pageable);
    }

    @Override
    public Page<Booking> getBookingsRented(Pageable pageable) {
        return bookingRepository
                .findByBookingStateCode(BookingStateCodeConstants.REN, pageable);
    }

    @Override
    public Page<Booking> getBookingsReserved(Pageable pageable) {
        return bookingRepository
                .findByBookingStateCode(BookingStateCodeConstants.RES, pageable);
    }

    @Override
    public Page<Booking> getBookingsCanceled(Pageable pageable) {
        return bookingRepository
                .findByBookingStateCode(BookingStateCodeConstants.CAN, pageable);
    }

    @Override
    public Page<Booking> getBookingsReturned(Pageable pageable) {
        return bookingRepository
                .findByBookingStateCode(BookingStateCodeConstants.RET, pageable);
    }

    @Override
    public Booking getBookingById(Long bookingId) throws BookingNotFoundException {
        return bookingRepository
                .findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException(bookingId));
    }

    @Override
    public Page<Booking> getUserBookings(Long userId, Pageable pageable) throws UserNotFoundException {
        checkIfUserExists(userId);
        return bookingRepository.findByUserId(userId, pageable);
    }

    @Override
    public Page<Booking> getUserBookingsReserved(Long userId, Pageable pageable) throws UserNotFoundException {
        checkIfUserExists(userId);
        return bookingRepository
                .findByUserIdAndBookingStateCode(userId, BookingStateCodeConstants.RES, pageable);
    }

    @Override
    public Page<Booking> getUserBookingsRented(Long userId, Pageable pageable) throws UserNotFoundException {
        checkIfUserExists(userId);
        return bookingRepository
                .findByUserIdAndBookingStateCode(userId, BookingStateCodeConstants.REN, pageable);
    }

    @Override
    public Map<String, BigDecimal> countCost(Booking booking) throws BookingNotFoundException, VehicleNotFoundException {
        BigDecimal itemCost;
        BigDecimal totalCost;

        LocalDateTime receiptDate = booking.getReceiptDate();
        LocalDateTime returnDate = booking.getReturnDate();

        long dateDiff = receiptDate.until(returnDate, DAYS);

        Vehicle selectedVehicle = vehicleService.getById(booking.getVehicle().getId());

        itemCost = selectedVehicle.getDailyFee();
        totalCost = itemCost.multiply(BigDecimal.valueOf(dateDiff));

        return Collections.singletonMap("totalCost", totalCost);
    }

    private void checkIfUserExists(Long userId) throws UserNotFoundException {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }
    }

    private void checkIfBookingExists(Long bookingId) throws BookingNotFoundException {
        if (!bookingRepository.existsById(bookingId)) {
            throw new BookingNotFoundException(bookingId);
        }
    }

}
