package org.senla.woodygray.repository;

import org.senla.woodygray.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    public List<User> getAllUsers();
    public Optional<User> findByPhoneNumber(String username);
    public void saveUser(User user);
}
