package org.senla.woodygray.service;

import lombok.RequiredArgsConstructor;
import org.senla.woodygray.exceptions.UserNotFoundException;
import org.senla.woodygray.model.User;
import org.senla.woodygray.repository.RoleRepository;
import org.senla.woodygray.repository.UserRepository;
import org.senla.woodygray.util.JwtTokenUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtTokenUtils jwtTokenUtils;

    @Transactional
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Transactional
    public Optional<User> findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    @Transactional
    public User findByToken(String token) throws UserNotFoundException {
        token = token.substring(7);
        Optional<User> optionalUser = userRepository.findByPhoneNumber(jwtTokenUtils.getUserPhoneNumber(token));
        User user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
            return user;
        } else {
            throw new UserNotFoundException("Can't find user by token");
        }
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userPhoneNumber) throws UsernameNotFoundException {
        User user = findByPhoneNumber(userPhoneNumber).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User with phone '%s' not found", userPhoneNumber)
        ));
        return new org.springframework.security.core.userdetails.User(
                user.getPhoneNumber(),
                user.getHashPassword(),
                Stream.of(new SimpleGrantedAuthority(user.getRole().getRoleName().toString())).toList()
        );
    }

    @Transactional
    public Long getUserIdFromToken(String token) throws UserNotFoundException {
        String userPhoneNumber = jwtTokenUtils.getUserPhoneNumber(token);
        Optional<User> user = findByPhoneNumber(userPhoneNumber);
        if (user.isEmpty()) {
            throw new UserNotFoundException("Can't find user or get phone number from token");
        }
        return user.get().getId();
    }

    @Transactional
    public void createNewUser(User user) {
        user.setRole(roleRepository.findByRoleName("ROLE_USER").get());
        //TODO:handle the null value
        user.setRating(0);
        userRepository.save(user);
    }

}
