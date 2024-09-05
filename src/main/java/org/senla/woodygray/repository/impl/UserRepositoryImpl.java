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

    private final EntityManagerFactory emf;


    @Override
    public Optional<User> findByPhoneNumber(String phoneNumber) {
        EntityManager session = emf.createEntityManager();
//        session.getTransaction().begin();

        User result = null;
        try {
            result = session
                    .createQuery("select u from User u join fetch u.role where u.phoneNumber = :phoneNumber", User.class)
                    .setParameter("phoneNumber", phoneNumber)
                    .getSingleResult();
        }catch (NoResultException e) {
            //do nothing cause use optional
        }

//        session.getTransaction().commit();
//        session.close();
        return Optional.ofNullable(result);
    }

    @Override
    public void save(User user) {
        EntityManager session = emf.createEntityManager();
        session.getTransaction().begin();

        session.persist(user);

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Optional<User> findById(Long id) {
        EntityManager session = emf.createEntityManager();
        session.getTransaction().begin();

        User result = null;
        try {
            result = session.find(User.class, id);
        }catch (NoResultException e) {
            //do nothing cause use optional
        }

        session.getTransaction().commit();
        session.close();
        return Optional.ofNullable(result);
    }

    @Override
    public void update(User user) {
        EntityManager session = emf.createEntityManager();
        session.getTransaction().begin();

        session.merge(user);

        session.getTransaction().commit();
        session.close();
    }
}
