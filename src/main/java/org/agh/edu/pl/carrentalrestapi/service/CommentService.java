package org.agh.edu.pl.carrentalrestapi.service;

import org.agh.edu.pl.carrentalrestapi.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentService {
    Page<Comment> getAllForVehiclePerPage(@Param("id") Long id, Pageable pageable);
    Long addComment(Comment comment);
    List<Comment> getAllForVehicle(Long id);
    void deleteComment(Long id);
    Long fullUpdateComment(Long id, Comment comment);
    Long partialUpdateComment(Long id, Comment comment);

}
