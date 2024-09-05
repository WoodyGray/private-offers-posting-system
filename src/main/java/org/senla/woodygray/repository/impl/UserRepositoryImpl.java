package org.senla.woodygray.repository.impl;

import lombok.RequiredArgsConstructor;
import org.senla.woodygray.model.User;
import org.senla.woodygray.repository.UserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final EntityManager em;


    @Override
    public Optional<User> findByPhoneNumber(String phoneNumber) {

        User result = null;
        try {
            result = em
                    .createQuery("select u from User u join fetch u.role where u.phoneNumber = :phoneNumber", User.class)
                    .setParameter("phoneNumber", phoneNumber)
                    .getSingleResult();
        }catch (Exception e) {
            return Optional.empty();
        }

        return Optional.ofNullable(result);
    }

    @Override
    public void save(User user) {
        em.persist(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        User result = null;
        try {
            result = em.find(User.class, id);
        }catch (Exception e) {
            return Optional.empty();
        }
        return Optional.ofNullable(result);
    }

    @Override
    public void update(User user) {
        em.merge(user);
    }
}
