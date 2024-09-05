package org.senla.woodygray.repository.impl;

import lombok.RequiredArgsConstructor;
import org.senla.woodygray.model.OfferStatus;
import org.senla.woodygray.model.OfferStatusType;
import org.senla.woodygray.repository.OfferStatusRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OfferStatusRepositoryImpl implements OfferStatusRepository {


    private final EntityManager em;

    @Override
    public Optional<OfferStatus> getPublishedStatus() {

        OfferStatus result = em
                .createQuery("select os from OfferStatus os where os.statusType = :statusType", OfferStatus.class)
                .setParameter("statusType", OfferStatusType.PUBLISHED)
                .getSingleResult();
        //TODO:оставить этот метод или сделать универсальный?

        return Optional.ofNullable(result);

    }
}
