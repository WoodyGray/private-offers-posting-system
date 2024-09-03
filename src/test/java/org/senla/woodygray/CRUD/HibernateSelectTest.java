package org.senla.woodygray.CRUD;


import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.senla.woodygray.dtos.OfferDto;
import org.senla.woodygray.model.*;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.*;
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
//        resultss.forEach(System.out::println);

        session.getTransaction().commit();
        session.close();

    }

    @Test
    void get_offers_from_db(){

        EntityManager session = emf.createEntityManager();
        session.getTransaction().begin();


        List<Offer> resultss = session.createQuery("select o from Offer o ", Offer.class)
                .getResultList();
//        resultss.forEach(System.out::println);

        session.getTransaction().commit();
        session.close();

    }

    @Test
    void get_offers_from_criteria_api_db(){
        EntityManager session = emf.createEntityManager();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Offer> cq = cb.createQuery(Offer.class);

        // Определяем основной корень запроса (корень сущности Offer)
        Root<Offer> offerRoot = cq.from(Offer.class);

        // Если требуется сделать Join с сущностью User
        Join<Offer, User> userJoin = offerRoot.join("user");

        // Выбираем все поля сущности Offer (полный объект Offer)
        cq.select(offerRoot);

        // Выполняем запрос и получаем результат
        List<Offer> offers = session.createQuery(cq).getResultList();

        // Закрываем сессию
        session.close();

        // Выводим результат
        System.out.println(offers);

    }

    @Test
    void get_offers_with_photo() {
        EntityManager session = emf.createEntityManager();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<OfferDto> cq = cb.createQuery(OfferDto.class);

        Root<Offer> offerRoot = cq.from(Offer.class);

        Join<Offer, User> userJoin = offerRoot.join("user");

        // Выбираем основные поля OfferDto без фотографий
        cq.select(cb.construct(OfferDto.class,
                userJoin.get("id"),
                offerRoot.get("title"),
                offerRoot.get("description"),
                offerRoot.get("price")
        ));

        List<OfferDto> offers = session.createQuery(cq).getResultList();

        // Теперь загружаем фотографии для каждого Offer
        for (OfferDto offer : offers) {
            List<Photo> photos = session.createQuery(
                            "select p from Photo p where p.offer.id = :offerId", Photo.class)
                    .setParameter("offerId", offer.getUserId())  // Нужно получить идентификатор Offer из OfferDto
                    .getResultList();
            offer.setPhotos(photos);  // Нужно добавить setPhotos(List<Photo>) в OfferDto
        }

    }


    @Test
    void get_offer_status_from_db(){

        EntityManager session = emf.createEntityManager();
        session.getTransaction().begin();


        List<OfferStatus> resultss = session.createQuery("select o from OfferStatus o ", OfferStatus.class)
                .getResultList();
//        resultss.forEach(System.out::println);


        session.getTransaction().commit();
        session.close();

    }

    @Test
    void get_photos_from_db(){

        EntityManager session = emf.createEntityManager();
        session.getTransaction().begin();


        List<Photo> resultss = session.createQuery("select p from Photo p ", Photo.class)
                .getResultList();
//        resultss.forEach(System.out::println);


        session.getTransaction().commit();
        session.close();

    }

    @Test
    void get_comments_from_db(){

        EntityManager session = emf.createEntityManager();
        session.getTransaction().begin();


        List<Comment> resultss = session.createQuery("select c from Comment c ", Comment.class)
                .getResultList();
//        resultss.forEach(comment -> System.out.println(comment.getId() + " " + comment.getMessageText()));


        session.getTransaction().commit();
        session.close();

    }

    @Test
    void get_reviews_from_db(){

        EntityManager session = emf.createEntityManager();
        session.getTransaction().begin();


        List<Review> resultss = session.createQuery("select r from Review r ", Review.class)
                .getResultList();
//        resultss.forEach(System.out::println);


        session.getTransaction().commit();
        session.close();

    }

    @Test
    void get_chat_message_from_db(){

        EntityManager session = emf.createEntityManager();
        session.getTransaction().begin();


        List<ChatMessage> resultss = session.createQuery("select cm from ChatMessage cm ", ChatMessage.class)
                .getResultList();
//        resultss.forEach(System.out::println);


        session.getTransaction().commit();
        session.close();

    }
}
