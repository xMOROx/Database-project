package org.agh.edu.pl.carrentalrestapi.repository;

import org.agh.edu.pl.carrentalrestapi.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository("commentRepository")
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value="SELECT c FROM Comment c WHERE c.vehicle.id=:vehicleId ORDER BY c.creationDate DESC",
            countQuery="SELECT COUNT(c) FROM Comment c WHERE c.vehicle.id=:vehicleId")
    Page<Comment> findCommentsByVehicleId(@Param("vehicleId") Long vehicleId, Pageable pageable);
    @Query("SELECT c FROM Comment c WHERE c.vehicle.id=:vehicleId ORDER BY c.creationDate DESC")
    List<Comment> findAllCommentsByVehicleId(@Param("vehicleId") Long vehicleId);
    @Query(value = "SELECT c FROM Comment c WHERE c.vehicle.id=:id ORDER BY c.creationDate", countQuery="SELECT COUNT(c) FROM Comment c WHERE c.vehicle.id=:id")
    Page<Comment> findAllForVehicle(Long id, Pageable pageable);
}
