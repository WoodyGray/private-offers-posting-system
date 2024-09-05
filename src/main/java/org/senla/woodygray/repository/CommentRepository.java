package org.senla.woodygray.repository;

import org.senla.woodygray.model.Comment;

import java.util.List;

public interface CommentRepository {
    List<Comment> findAllByOfferId(Long offerId);

    void save(Comment comment);

    void deleteById(Long id);
}
