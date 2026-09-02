package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.mockito.Mockito.*;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {
//    @Autowired // service
//    private UserDetailsServiceImpl userDetailsService;
//
//    @MockBean // dependency inside service which take time and now provide dummy to test
//    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;

    // we use this  : @ExtendWith(MockitoExtension.class)
//    @BeforeEach
//    void setup(){
//        MockitoAnnotations.initMocks(this);
//    }

    @Test
    void loadUserByUsernameTest(){
        // when a method call take palce - which method
        when(userRepository.findByUserName(ArgumentMatchers.anyString()))
                .thenReturn(
                        User.builder()
                                .userName("ram")
                                .password("inrind")
                                .roles(new ArrayList<>())
                                .build());
        UserDetails user = userDetailsService.loadUserByUsername(("ram"));
        Assertions.assertNotNull(user);
    }

}
