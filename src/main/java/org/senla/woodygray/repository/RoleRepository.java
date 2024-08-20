package org.senla.woodygray.repository;

import org.senla.woodygray.model.Role;

import java.util.Optional;

public interface RoleRepository {
    public Optional<Role> findByRoleName(String roleName);
}
