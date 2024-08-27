package org.senla.woodygray.CRUD;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.senla.woodygray.model.User;


import java.util.List;

public class HibernateInsertTest {
    private EntityManagerFactory emf;


    @BeforeEach
    protected void setUp() throws Exception{
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();

        try {
            emf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        }catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
        }

    }

    @Test
    void insert_user_in_db(){

        EntityManager session = emf.createEntityManager();
        session.getTransaction().begin();


        List<User> resultss = session.createQuery("select u from User u join fetch u.role", User.class)
                .getResultList();
        resultss.forEach(System.out::println);

        session.getTransaction().commit();
        session.close();

    }
}
