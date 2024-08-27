package org.senla.woodygray.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.senla.woodygray.model.User;
import org.senla.woodygray.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    private EntityManagerFactory emf;

    @Override
    public List<User> getAllUsers() {
        EntityManager session = emf.createEntityManager();
        session.getTransaction().begin();


        List<User> results = session.createQuery("select u from User u join fetch u.role", User.class)
                .getResultList();
        results.forEach(System.out::println);

        session.getTransaction().commit();
        session.close();
        return results;
    }

    @Override
    public Optional<User> findByPhoneNumber(String phoneNumber) {
        EntityManager session = emf.createEntityManager();
        session.getTransaction().begin();

        User result = session
                .createQuery("select u from User u join fetch u.role where u.phoneNumber = :phoneNumber", User.class)
                .setParameter("phoneNumber", phoneNumber)
                .getSingleResult();

        session.getTransaction().commit();
        session.close();
        return Optional.ofNullable(result);
    }

    @Override
    public void saveUser(User user) {
        EntityManager session = emf.createEntityManager();
        session.getTransaction().begin();

        session.persist(user);

        session.getTransaction().commit();
        session.close();
    }
}
