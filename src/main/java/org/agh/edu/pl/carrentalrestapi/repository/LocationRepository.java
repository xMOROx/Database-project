package org.agh.edu.pl.carrentalrestapi.repository;

import org.agh.edu.pl.carrentalrestapi.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository("locationRepository")
public interface LocationRepository extends JpaRepository<Location, Long> {
    List<String> findCityList();
    @Query("SELECT l FROM Location l WHERE l.email = ?1")
    @Transactional
    Optional<Location> findByEmail(String email);
    @Query("SELECT l FROM Location l WHERE l.phoneNumber = ?1")
    @Transactional
    Optional<Location> findByPhoneNumber(String phoneNumber);
    @Query("SELECT DISTINCT l.city FROM Location l ORDER BY l.city ASC")
    List<String> findAllCities();
}
