package org.senla.woodygray.repository.impl;

import lombok.RequiredArgsConstructor;
import org.senla.woodygray.model.Offer;
import org.senla.woodygray.model.OfferStatus;
import org.senla.woodygray.model.User;
import org.senla.woodygray.repository.OfferRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OfferRepositoryImpl implements OfferRepository {

    private final EntityManagerFactory emf;

    @Override
    public List<Offer> findAll() {
        EntityManager session = emf.createEntityManager();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Offer> cq = cb.createQuery(Offer.class);

        Root<Offer> offerRoot = cq.from(Offer.class);
        offerRoot.fetch("photos", JoinType.LEFT);

        cq.select(offerRoot).distinct(true);

        List<Offer> offers = session.createQuery(cq).getResultList();

        session.close();

        return offers;
    }    //TODO: подумать над оптимизацией

    @Override
    public List<Offer> findAllByUserId(Long userId) {
        EntityManager session = emf.createEntityManager();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Offer> cq = cb.createQuery(Offer.class);

        Root<Offer> offerRoot = cq.from(Offer.class);
        Join<Offer, User> userJoin = offerRoot.join("user");

        Predicate userIdPredicate = cb.equal(userJoin.get("id"), userId);

        cq.where(userIdPredicate);
        cq.select(offerRoot);

        List<Offer> offers = session.createQuery(cq).getResultList();

        session.close();

        return offers;
    }

    @Override
    public Optional<Long> findOfferIdByTitle(String title) {
        EntityManager session = emf.createEntityManager();

        Long result = session
                .createQuery("select o.id from Offer o where o.title = :title", Long.class)
                .setParameter("title", title)
                .getSingleResult();


        session.close();
        return Optional.ofNullable(result);
        //TODO: сделать проверку только среди объявлений пользователей
    }


    @Override
    public List<Offer> findByTitleContainingOrDescriptionContaining(String title, String description) {
        EntityManager session = emf.createEntityManager();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Offer> cq = cb.createQuery(Offer.class);

        Root<Offer> offerRoot = cq.from(Offer.class);
        offerRoot.fetch("photos", JoinType.LEFT);

        cq.select(offerRoot).distinct(true);

        Predicate titlePredicate = cb.like(offerRoot.get("title"), "%" + title + "%");
        Predicate descriptionPredicate = cb.like(offerRoot.get("description"), "%" + description + "%");

        cq.where(cb.or(titlePredicate, descriptionPredicate));

        List<Offer> offers = session.createQuery(cq).getResultList();

        session.close();
        return offers;
    }    //TODO: подумать над оптимизацией


    @Override
    public void save(Offer offer) {
        EntityManager session = emf.createEntityManager();
        session.getTransaction().begin();

        session.persist(offer);

        session.getTransaction().commit();
        session.close();
        //TODO: без явного объявления транзакции не выполняются запросы
    }

    @Override
    public void updateStatus(Long offerId, Long offerStatusId) {
        EntityManager session = emf.createEntityManager();
        session.getTransaction().begin();

        OfferStatus offerStatus = session.find(OfferStatus.class, offerStatusId);
        Offer offer = session.find(Offer.class, offerId);
        offer.setOfferStatus(offerStatus);

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Offer> findAllByUserPhoneNumber(String userPhoneNumber) {
        EntityManager session = emf.createEntityManager();

        List<Offer> result = session
                .createQuery("select o from Offer o where o.user.phoneNumber = :userPhoneNumber", Offer.class)
                .setParameter("userPhoneNumber", userPhoneNumber)
                .getResultList();


        session.close();
        return result;
    }

    @Override
    public Optional<Offer> findById(Long offerID) {
        EntityManager session = emf.createEntityManager();

        Offer offer = session.find(Offer.class, offerID);

//        session.close();
        return Optional.ofNullable(offer);
    }

    @Override
    public void update(Offer offer) {
        EntityManager session = emf.createEntityManager();
        session.getTransaction().begin();

        session.merge(offer);

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void deletePhotosFromOffer(Long id) {
        EntityManager session = emf.createEntityManager();
        session.getTransaction().begin();

        Query query = session.createQuery("delete from Photo p where p.offer.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();

        //TODO: не работает данная реализация, почему?
//        Offer offer = session.find(Offer.class, id);
//        offer.getPhotos().clear();
//        session.merge(offer);

        session.getTransaction().commit();
        session.close();
    }

}
