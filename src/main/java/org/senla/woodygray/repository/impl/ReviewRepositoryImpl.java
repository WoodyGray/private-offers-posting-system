package org.senla.woodygray.repository.impl;

import lombok.RequiredArgsConstructor;
import org.senla.woodygray.model.Review;
import org.senla.woodygray.repository.ReviewRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepository {

    private final EntityManager em;

    @Override
    public void save(Review review) {
        em.persist(review);
    }

    @Override
    public List<Review> getAllByUserId(Long userId)
    {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Review> cq = cb.createQuery(Review.class);
        Root<Review> root = cq.from(Review.class);

        Predicate predicate = cb.equal(root.get("seller").get("id"), userId);

        cq.where(predicate);
        cq.select(root);

        return em.createQuery(cq).getResultList();
    }

    @Override
    public void delete(Review review) {
        em.remove(review);
    }

    @Override
    public Optional<Review> getById(Long id) {
        return Optional.ofNullable(em.find(Review.class, id));
    }
}
