package org.senla.woodygray.repository;

import org.senla.woodygray.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    public Optional<User> findByPhoneNumber(String username);

    public void save(User user);

    public Optional<User> findById(Long id);

    public void update(User user);
}
