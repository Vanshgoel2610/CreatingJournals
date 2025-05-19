package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.UserEntry;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ActiveProfiles("dev")
//@SpringBootTest
public class UserDetailsServiceImplTest {

/*    Not using @SpringBootTest to speed up the process
      but then userDetailsService will become null, so use InjectMocks as it will inject all mocks
      @Mocks is used for that components that are used in UserDetailsServiceImpl in this case.
      @BeforeEach is used as when @Autowired is not used then userRepository will become null and after using @BeforeEach userRepository will be initialised and gets injected using @InjectMocks
*/

//    @Autowired
    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

//    @MockBean
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void loadUserByUsernameTest() {
        // Instead of UserEntry.builder().username("vansh").password("kkk").build()
        UserEntry userEntry = new UserEntry();
        userEntry.setUserName("vansh");
        userEntry.setPassword("kkk");
        userEntry.setRoles(Collections.singletonList("ROLEUSER")); // 🔥 This line fixes the NullPointerException


        when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(userEntry);
//        when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(UserEntry.builder().username("vansh").password("kkk").build());

        // When
        UserDetails user = userDetailsService.loadUserByUsername("ram");

        // Then
        assertNotNull(user);
        assertEquals("vansh", user.getUsername());
        assertEquals("kkk", user.getPassword());
    }
}