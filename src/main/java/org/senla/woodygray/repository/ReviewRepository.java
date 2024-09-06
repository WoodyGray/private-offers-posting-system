package org.senla.woodygray.repository;

import org.senla.woodygray.model.Review;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ReviewRepository {
    void save(Review review);

    List<Review> getAllByUserId(Long userId);

    void delete(Review review);

    Optional<Review> getById(Long id);
}
