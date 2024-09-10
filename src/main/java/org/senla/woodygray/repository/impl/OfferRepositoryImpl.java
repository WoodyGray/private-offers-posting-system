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

    private final EntityManager em;

    @Override
    public List<Offer> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Offer> cq = cb.createQuery(Offer.class);

        Root<Offer> offerRoot = cq.from(Offer.class);
        offerRoot.fetch("photos", JoinType.LEFT);

        cq.select(offerRoot).distinct(true);

        List<Offer> offers = em.createQuery(cq).getResultList();

        return offers;
    }

    @Override
    public List<Offer> findAllByUserId(Long userId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Offer> cq = cb.createQuery(Offer.class);

        Root<Offer> offerRoot = cq.from(Offer.class);
        Join<Offer, User> userJoin = offerRoot.join("user");

        Predicate userIdPredicate = cb.equal(userJoin.get("id"), userId);

        cq.where(userIdPredicate);
        cq.select(offerRoot);

        List<Offer> offers = em.createQuery(cq).getResultList();

        return offers;
    }

    @Override
    public List<Offer> findByTitleContainingOrDescriptionContaining(String title, String description) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Offer> cq = cb.createQuery(Offer.class);

        Root<Offer> offerRoot = cq.from(Offer.class);
        offerRoot.fetch("photos", JoinType.LEFT);

        cq.select(offerRoot).distinct(true);

        Predicate titlePredicate = cb.like(offerRoot.get("title"), "%" + title + "%");
        Predicate descriptionPredicate = cb.like(offerRoot.get("description"), "%" + description + "%");

        cq.where(cb.or(titlePredicate, descriptionPredicate));

        List<Offer> offers = em.createQuery(cq).getResultList();

        return offers;
    }


    @Override
    public void save(Offer offer) {
        em.persist(offer);
    }

    @Override
    public void updateStatus(Long offerId, Long offerStatusId) {

        OfferStatus offerStatus = em.find(OfferStatus.class, offerStatusId);
        Offer offer = em.find(Offer.class, offerId);
        offer.setOfferStatus(offerStatus);

    }


    @Override
    public Optional<Offer> findById(Long offerID) {

        Offer offer = em.find(Offer.class, offerID);

        return Optional.ofNullable(offer);
    }

    @Override
    public void update(Offer offer) {
        em.merge(offer);
    }

    @Override
    public void deletePhotosFromOffer(Long id) {

        Query query = em.createQuery("delete from Photo p where p.offer.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();

        //TODO: не работает данная реализация, почему?
//        Offer offer = em.find(Offer.class, id);
//        offer.getPhotos().clear();
//        em.merge(offer);
        //em.flush

    }

    @Override
    public List<Offer> findSoldByUserId(Long userId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Offer> cq = cb.createQuery(Offer.class);
        Root<Offer> offerRoot = cq.from(Offer.class);

        cq.select(offerRoot);

        Predicate userIdPredicate = cb.equal(offerRoot.get("user").get("id"), userId);
        Predicate soldStatusPredicate = cb.equal(offerRoot.get("offerStatus").get("id"), 2);
        cq.where(cb.and(userIdPredicate, soldStatusPredicate));

        return em.createQuery(cq).getResultList();
    }

}
