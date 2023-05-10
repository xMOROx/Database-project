package org.agh.edu.pl.carrentalrestapi.service;

import org.agh.edu.pl.carrentalrestapi.entity.Comment;
import org.agh.edu.pl.carrentalrestapi.exception.types.CommentNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentService {
    Page<Comment> getAllForVehiclePerPage(@Param("id") Long id, Pageable pageable);
    Long addComment(Comment comment);
    Page<Comment> getAllForVehicle(Long id, Pageable pageable) throws CommentNotFoundException;
    void deleteComment(Long id) throws CommentNotFoundException;
    Long fullUpdateComment(Long id, Comment comment) ;
    Long partialUpdateComment(Long id, Comment comment) throws CommentNotFoundException;

}
