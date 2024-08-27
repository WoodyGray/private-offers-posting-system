package org.senla.woodygray.service;

import lombok.AllArgsConstructor;
import org.senla.woodygray.model.User;
import org.senla.woodygray.repository.RoleRepository;
import org.senla.woodygray.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService  {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public List<User> getAllUsers(){
        return userRepository.getAllUsers();
    }

    public Optional<User> findByPhoneNumber(String phoneNumber){
        return userRepository.findByPhoneNumber(phoneNumber);
    }



    @Transactional
    public void createNewUser(User user){
        user.setRole(roleRepository.findByRoleName("ROLE_USER").get());
        //TODO:handle the null value
        user.setRating(0);
        userRepository.saveUser(user);
    }
}
