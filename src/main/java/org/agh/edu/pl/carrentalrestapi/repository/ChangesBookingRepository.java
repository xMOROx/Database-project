package org.agh.edu.pl.carrentalrestapi.repository;

import org.agh.edu.pl.carrentalrestapi.entity.ChangesBooking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("changesBookingRepository")
public interface ChangesBookingRepository extends JpaRepository<ChangesBooking, Long> {
    @Query(value = "SELECT cb FROM ChangesBooking cb", countQuery = "SELECT COUNT(cb) FROM ChangesBooking cb")
    Page<ChangesBooking> findChangesBookingsForPage(Pageable pageable);
}
