package org.senla.woodygray.repository.impl;

import lombok.RequiredArgsConstructor;
import org.senla.woodygray.model.Comment;
import org.senla.woodygray.repository.CommentRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {

    private final EntityManagerFactory emf;

    @Override
    public List<Comment> findAllByOfferId(Long offerId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Query query = em.createQuery("select c from Comment c where c.id = :offerId");
        query.setParameter("offerId", offerId);
        List<Comment> comments = query.getResultList();

        em.getTransaction().commit();
        return comments;
    }

    @Override
    public void save(Comment comment) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        em.persist(comment);

        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void deleteById(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            Comment comment = em.find(Comment.class, id);
            em.remove(comment);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }

        em.getTransaction().commit();
        em.close();
    }
}
