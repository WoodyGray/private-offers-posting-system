package org.senla.woodygray.repository.impl;

import lombok.RequiredArgsConstructor;
import org.senla.woodygray.model.OfferStatus;
import org.senla.woodygray.model.OfferStatusType;
import org.senla.woodygray.model.Role;
import org.senla.woodygray.repository.OfferStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OfferStatusRepositoryImpl implements OfferStatusRepository {


    private final EntityManagerFactory emf;

    @Override
    public Optional<OfferStatus> getPublishedStatus() {
        EntityManager session = emf.createEntityManager();

        OfferStatus result = session
                .createQuery("select os from OfferStatus os where os.statusType = :statusType", OfferStatus.class)
                .setParameter("statusType", OfferStatusType.PUBLISHED)
                .getSingleResult();
        //TODO:норм?
        session.close();
        return Optional.ofNullable(result);

    }
}
