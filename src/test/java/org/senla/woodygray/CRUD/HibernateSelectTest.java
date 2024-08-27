package org.senla.woodygray.CRUD;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.senla.woodygray.model.*;


import java.util.List;

public class HibernateSelectTest {

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
    void get_users_from_db(){

        EntityManager session = emf.createEntityManager();
        session.getTransaction().begin();


        List<User> resultss = session.createQuery("select u from User u join fetch u.role", User.class)
                .getResultList();
        resultss.forEach(System.out::println);

        session.getTransaction().commit();
        session.close();

    }

    @Test
    void get_offers_from_db(){

        EntityManager session = emf.createEntityManager();
        session.getTransaction().begin();


        List<Offer> resultss = session.createQuery("select o from Offer o ", Offer.class)
                .getResultList();
        resultss.forEach(System.out::println);

        session.getTransaction().commit();
        session.close();

    }

    @Test
    void get_offer_status_from_db(){

        EntityManager session = emf.createEntityManager();
        session.getTransaction().begin();


        List<OfferStatus> resultss = session.createQuery("select o from OfferStatus o ", OfferStatus.class)
                .getResultList();
        resultss.forEach(System.out::println);


        session.getTransaction().commit();
        session.close();

    }

    @Test
    void get_photos_from_db(){

        EntityManager session = emf.createEntityManager();
        session.getTransaction().begin();


        List<Photo> resultss = session.createQuery("select p from Photo p ", Photo.class)
                .getResultList();
        resultss.forEach(System.out::println);


        session.getTransaction().commit();
        session.close();

    }

    @Test
    void get_comments_from_db(){

        EntityManager session = emf.createEntityManager();
        session.getTransaction().begin();


        List<Comment> resultss = session.createQuery("select c from Comment c ", Comment.class)
                .getResultList();
        resultss.forEach(comment -> System.out.println(comment.getId() + " " + comment.getMessageText()));


        session.getTransaction().commit();
        session.close();

    }

    @Test
    void get_reviews_from_db(){

        EntityManager session = emf.createEntityManager();
        session.getTransaction().begin();


        List<Review> resultss = session.createQuery("select r from Review r ", Review.class)
                .getResultList();
        resultss.forEach(System.out::println);


        session.getTransaction().commit();
        session.close();

    }

    @Test
    void get_chat_message_from_db(){

        EntityManager session = emf.createEntityManager();
        session.getTransaction().begin();


        List<ChatMessage> resultss = session.createQuery("select cm from ChatMessage cm ", ChatMessage.class)
                .getResultList();
        resultss.forEach(System.out::println);


        session.getTransaction().commit();
        session.close();

    }
}
