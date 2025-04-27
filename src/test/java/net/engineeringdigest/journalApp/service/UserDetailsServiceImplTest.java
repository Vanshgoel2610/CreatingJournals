package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.UserEntry;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserDetailsServiceImplTest {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsernameTest() {
        // Instead of UserEntry.builder().username("vansh").password("kkk").build()
        UserEntry userEntry = new UserEntry();
        userEntry.setUserName("vansh");
        userEntry.setPassword("kkk");

        when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(userEntry);
//        when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(UserEntry.builder().username("vansh").password("kkk").build());

        // When
        UserDetails user = userDetailsService.loadUserByUsername("vansh");

        // Then
        assertNotNull(user);
        assertEquals("vansh", user.getUsername());
        assertEquals("kkk", user.getPassword());
    }
}