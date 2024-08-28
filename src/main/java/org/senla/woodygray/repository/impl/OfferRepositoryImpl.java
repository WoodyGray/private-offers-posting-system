package org.senla.woodygray.repository.impl;

import org.senla.woodygray.dtos.OfferDto;
import org.senla.woodygray.model.Offer;
import org.senla.woodygray.model.User;
import org.senla.woodygray.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.*;
import java.util.List;

@Repository
public class OfferRepositoryImpl implements OfferRepository {
    @Autowired
    private EntityManagerFactory emf;

    @Override
    public List<OfferDto> findByTitleContainingOrDescriptionContaining(String title, String description) {
        EntityManager session = emf.createEntityManager();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<OfferDto> cq = cb.createQuery(OfferDto.class);

        Root<Offer> offerRoot = cq.from(Offer.class);

        Join<Offer, User> userJoin = offerRoot.join("user");

        cq.select(cb.construct(OfferDto.class,
                userJoin.get("id"),
                offerRoot.get("title"),
                offerRoot.get("description"),
                offerRoot.get("price")
        ));

        Predicate titlePredicate = cb.like(offerRoot.get("title"), "%"+title+"%");
        Predicate descriptionPredicate = cb.like(offerRoot.get("description"), "%"+description+"%");

        cq.where(cb.or(titlePredicate, descriptionPredicate));

        List<OfferDto> results = session.createQuery(cq).getResultList();

        session.close();
        return results;
    }
}
