package org.senla.woodygray.service;

import lombok.RequiredArgsConstructor;
import org.mapstruct.control.MappingControl;
import org.senla.woodygray.dtos.RegistrationUserDto;
import org.senla.woodygray.dtos.UserChangesDto;
import org.senla.woodygray.dtos.mapper.UserMapper;
import org.senla.woodygray.exceptions.UserModificationException;
import org.senla.woodygray.exceptions.UserNotFoundException;
import org.senla.woodygray.model.User;
import org.senla.woodygray.repository.RoleRepository;
import org.senla.woodygray.repository.UserRepository;
import org.senla.woodygray.util.JwtTokenUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.RoleNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final JwtTokenUtils jwtTokenUtils;
    private final UserMapper userMapper;


    @Transactional
    public Optional<User> findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    @Transactional
    public User findById(Long id) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException(id);
        }

        return optionalUser.get();

    }

    @Transactional
    public User findByToken(String token) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findByPhoneNumber(jwtTokenUtils.getUserPhoneNumber(token));
        User user;
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("Can't find user by token");
        }
        user = optionalUser.get();
        return user;

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
    public void createNewUser(User user) throws RoleNotFoundException {
        user.setRole(roleService.findByRoleName("ROLE_USER"));
        //TODO:handle the null value
        user.setRating(0.0);
        userRepository.save(user);
    }

    @Transactional
    public User createNewUser(RegistrationUserDto userDto, PasswordEncoder passwordEncoder) throws RoleNotFoundException {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setHashPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(roleService.findByRoleName("ROLE_USER"));
        //TODO:handle the null value
        //TODO:user mapstruckt
        user.setRating(0.0);
        userRepository.save(user);
        return user;
    }

    @Transactional
    public ResponseEntity<?> updateUser(Long id, UserChangesDto userChangesDto, String token) throws UserNotFoundException, UserModificationException {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (!jwtTokenUtils.getUserPhoneNumber(token).equals(user.getPhoneNumber())) {
                throw new UserModificationException(jwtTokenUtils.getUserPhoneNumber(token), id);
            }
            userMapper.updateUserFromDto(userChangesDto, user);
            userRepository.update(user);
            return ResponseEntity.ok("Update user success");
        } else {
            throw new UserNotFoundException(id);
        }
    }

    @Transactional
    public void update(User seller) {
        userRepository.update(seller);
    }
}
