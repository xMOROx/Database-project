package org.agh.edu.pl.carrentalrestapi.service.impl;

import org.agh.edu.pl.carrentalrestapi.entity.Booking;
import org.agh.edu.pl.carrentalrestapi.exception.types.BookingNotFoundException;
import org.agh.edu.pl.carrentalrestapi.exception.types.BookingUnavailableVehicleException;
import org.agh.edu.pl.carrentalrestapi.exception.types.UserNotFoundException;
import org.agh.edu.pl.carrentalrestapi.repository.BookingRepository;
import org.agh.edu.pl.carrentalrestapi.repository.UserRepository;
import org.agh.edu.pl.carrentalrestapi.service.BookingService;
import org.agh.edu.pl.carrentalrestapi.utils.enums.BookingStateCodeConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("bookingService")
@Transactional
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    public BookingServiceImpl(BookingRepository bookingRepository,
                              UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Long addBooking(Booking booking) throws BookingUnavailableVehicleException {
        Booking saved = bookingRepository.save(booking);
        return saved.getId();
    }

    @Override
    public void cancelBooking(Long bookingId) throws BookingNotFoundException {
        checkIfBookingExists(bookingId);
        bookingRepository.cancelBooking(bookingId);
    }

    @Override
    public void bookingRent(Long bookingId) throws BookingNotFoundException {
        checkIfBookingExists(bookingId);
        bookingRepository.bookingRent(bookingId);
    }

    @Override
    public void bookingReturn(Long bookingId) throws BookingNotFoundException {
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
    public Page<Booking> getBookingsCanceledForPage(Pageable pageable) {
        return bookingRepository
                .findByBookingStateCode(BookingStateCodeConstants.CAN, pageable);
    }

    @Override
    public Page<Booking> getBookingsReturned(Pageable pageable) {
        return bookingRepository
                .findByBookingStateCode(BookingStateCodeConstants.RET, pageable);
    }

    @Override
    public Booking getBookingsById(Long bookingId) throws BookingNotFoundException {
        return bookingRepository
                .findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException(bookingId));
    }

    @Override
    public Page<Booking> getAllBookings(Pageable pageable) {
        return bookingRepository.findAll(pageable);
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
