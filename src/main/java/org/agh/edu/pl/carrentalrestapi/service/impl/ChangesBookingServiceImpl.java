package org.agh.edu.pl.carrentalrestapi.service.impl;

import org.agh.edu.pl.carrentalrestapi.entity.ChangesBooking;
import org.agh.edu.pl.carrentalrestapi.repository.ChangesBookingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.agh.edu.pl.carrentalrestapi.service.ChangesBookingService;
@Service("ChangeBookingService")
@Transactional
public class ChangesBookingServiceImpl implements ChangesBookingService {
    private final ChangesBookingRepository changeBookingRepository;
    public ChangesBookingServiceImpl(ChangesBookingRepository changeBookingRepository) {
        this.changeBookingRepository = changeBookingRepository;
    }
    @Override
    public Page<ChangesBooking> getChangesBookings(Pageable pageable) {
        return changeBookingRepository.findAll(pageable);
    }
}
