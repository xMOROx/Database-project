package org.agh.edu.pl.carrentalrestapi.service;

import org.agh.edu.pl.carrentalrestapi.entity.ChangesBooking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChangesBookingService {
    Page<ChangesBooking> getChangesBookings(Pageable pageable);
}
