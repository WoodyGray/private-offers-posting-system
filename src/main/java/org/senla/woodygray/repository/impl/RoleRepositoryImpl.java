package org.senla.woodygray.repository.impl;


import lombok.RequiredArgsConstructor;
import org.senla.woodygray.model.Role;
import org.senla.woodygray.model.RoleType;
import org.senla.woodygray.repository.RoleRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {

    private final EntityManager em;

    @Override
    public Optional<Role> findByRoleName(String roleName) {
        Role result = em
                .createQuery("select r from Role r where r.roleName = :roleName", Role.class)
                .setParameter("roleName", RoleType.valueOf(roleName))
                .getSingleResult();

        return Optional.ofNullable(result);
    }
}
