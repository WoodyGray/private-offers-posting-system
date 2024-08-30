package org.senla.woodygray.repository.impl;

import lombok.RequiredArgsConstructor;
import org.senla.woodygray.dtos.OfferDto;
import org.senla.woodygray.model.Offer;
import org.senla.woodygray.model.OfferStatus;
import org.senla.woodygray.model.Photo;
import org.senla.woodygray.model.User;
import org.senla.woodygray.repository.OfferRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OfferRepositoryImpl implements OfferRepository {

    private final EntityManagerFactory emf;

    @Override
    public List<OfferDto> findAll() {
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

        // загружаем фотографии для каждого Offer
        for (OfferDto offer : offers) {
            List<Photo> photos = session.createQuery(
                            "select p from Photo p where p.offer.id = :offerId", Photo.class)
                    .setParameter("offerId", offer.getUserId())
                    .getResultList();
            offer.setPhotos(photos);
        }

        return offers;
    }    //TODO: подумать над оптимизацией

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

        Predicate titlePredicate = cb.like(offerRoot.get("title"), "%" + title + "%");
        Predicate descriptionPredicate = cb.like(offerRoot.get("description"), "%" + description + "%");

        cq.where(cb.or(titlePredicate, descriptionPredicate));

        List<OfferDto> offers = session.createQuery(cq).getResultList();

        for (OfferDto offer : offers) {
            List<Photo> photos = session.createQuery(
                            "select p from Photo p where p.offer.id = :offerId", Photo.class)
                    .setParameter("offerId", offer.getUserId())
                    .getResultList();
            offer.setPhotos(photos);
        }

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

        session.close();
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

}
