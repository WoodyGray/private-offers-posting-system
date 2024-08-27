package org.senla.woodygray.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.senla.woodygray.model.Role;
import org.senla.woodygray.model.User;
import org.senla.woodygray.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Component
public class RoleRepositoryImpl implements RoleRepository {
    @Autowired
    private EntityManagerFactory emf;

    @Override
    public Optional<Role> findByRoleName(String roleName) {
        EntityManager session = emf.createEntityManager();
        session.getTransaction().begin();


        Role result = session
                .createQuery("select r from Role r where r.roleName = :roleName", Role.class)
                .setParameter("roleName", roleName)
                .getSingleResult();

        session.getTransaction().commit();
        session.close();
        return Optional.ofNullable(result);
    }
}
