package org.agh.edu.pl.carrentalrestapi.service.impl;

import org.agh.edu.pl.carrentalrestapi.entity.Comment;
import org.agh.edu.pl.carrentalrestapi.exception.CommentNotFoundException;
import org.agh.edu.pl.carrentalrestapi.repository.CommentRepository;
import org.agh.edu.pl.carrentalrestapi.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("commentService")
@Transactional
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Page<Comment> getAllForVehiclePerPage(@Param("id") Long id, Pageable pageable) {
        return commentRepository.findAllForVehicle(id, pageable);
    }

    @Override
    public Long addComment(Comment comment) {
        Comment saved = commentRepository.save(comment);
        return saved.getId();
    }

    @Override
    public List<Comment> getAllForVehicle(Long id) {
        return commentRepository.findAllCommentsByVehicleId(id);
    }

    @Override
    public void deleteComment(Long id) throws CommentNotFoundException {
        commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException(id));

        commentRepository.deleteById(id);
    }

    @Override
    public Long fullUpdateComment(Long id, Comment comment) {
        Comment commentToUpdate;
        try {
            commentToUpdate = commentRepository
                    .findById(id)
                    .orElseThrow(() -> new CommentNotFoundException(id));
        } catch (CommentNotFoundException e) {
            return addComment(comment);
        }

        commentToUpdate.setContent(comment.getContent());
        commentToUpdate.setRating(comment.getRating());
        commentToUpdate.setCreationDate(comment.getCreationDate());
        commentToUpdate.setModificationDate(comment.getModificationDate());

        commentRepository.save(commentToUpdate);

        return commentToUpdate.getId();
    }

    @Override
    public Long partialUpdateComment(Long id, Comment comment) throws CommentNotFoundException {
        Comment commentToUpdate = commentRepository
                .findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));

        if (comment.getContent() != null)
            commentToUpdate.setContent(comment.getContent());

        if (comment.getRating() != 0)
            commentToUpdate.setRating(comment.getRating());

        if (comment.getModificationDate() != null)
            commentToUpdate.setModificationDate(comment.getModificationDate());

        Comment saved = commentRepository.save(commentToUpdate);

        return saved.getId();

    }
}
