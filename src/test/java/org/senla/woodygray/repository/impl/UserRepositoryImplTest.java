package org.senla.woodygray.repository.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.senla.woodygray.model.User;

import javax.persistence.EntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserRepositoryImplTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private UserRepositoryImpl userRepository;

//    @Test
//    void testFindByPhoneNumber() {
//        String phoneNumber = "1234567890";
//        User user = new User();
//        user.setPhoneNumber(phoneNumber);
//
//        when(entityManager.createQuery(anyString(), eq(User.class)))
//            .thenReturn(mockTypedQuery);
//
//        when(mockTypedQuery.getSingleResult()).thenReturn(user);
//
//        Optional<User> result = userRepository.findByPhoneNumber(phoneNumber);
//
//        assertTrue(result.isPresent());
//        assertEquals(user, result.get());
//    }
}
