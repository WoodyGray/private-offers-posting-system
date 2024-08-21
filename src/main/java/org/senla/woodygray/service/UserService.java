package org.senla.woodygray.service;

import lombok.AllArgsConstructor;
import org.senla.woodygray.model.User;
import org.senla.woodygray.repository.RoleRepository;
import org.senla.woodygray.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public List<User> getAllUsers(){
        return userRepository.getAllUsers();
    }

    public Optional<User> findByPhoneNumber(String phoneNumber){
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        User user = findByPhoneNumber(phoneNumber).orElseThrow(() -> new UsernameNotFoundException(
                String.format("Can't find user by phone number '%s'", phoneNumber)
        ));

        return new org.springframework.security.core.userdetails.User(
                user.getPhoneNumber(),
                user.getHashPassword(),
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().getRoleName().toString()))
        );
    }

    public void createNewUser(User user){
        user.setRole(roleRepository.findByRoleName("ROLE_USER").get());
        //TODO:handle the null value
        user.setRating(0);
        userRepository.saveUser(user);
    }
}
