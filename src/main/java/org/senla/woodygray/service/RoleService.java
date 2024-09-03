package org.senla.woodygray.service;

import lombok.RequiredArgsConstructor;
import org.senla.woodygray.model.Role;
import org.senla.woodygray.repository.RoleRepository;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role findByRoleName(String roleName) throws RoleNotFoundException {
        Optional<Role> role = roleRepository.findByRoleName(roleName);
        if (role.isPresent()) {
            return role.get();
        }else {
            throw new RoleNotFoundException(roleName);
        }
    }

}
