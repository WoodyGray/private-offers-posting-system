package org.senla.woodygray.repository.impl;

import lombok.Data;
import org.senla.woodygray.model.User;
import org.senla.woodygray.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

@Data
@Component
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
}
