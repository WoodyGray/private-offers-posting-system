package org.senla.woodygray.repository.impl;

import lombok.RequiredArgsConstructor;
import org.senla.woodygray.model.Photo;
import org.senla.woodygray.repository.PhotoRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@Repository
@RequiredArgsConstructor
public class PhotoRepositoryImpl implements PhotoRepository {

    private final EntityManagerFactory emf;


    @Override
    public void save(Photo photo) {
        EntityManager session = emf.createEntityManager();
        session.getTransaction().begin();

        session.persist(photo);

        session.getTransaction().commit();
        session.close();
    }
}
