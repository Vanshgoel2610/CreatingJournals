package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.UserEntry;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

//    @BeforeAll
//    static void setUp() {
//
//    }

    @ParameterizedTest
    @ArgumentsSource(UserArgumentsProvider.class)
    public void testSaveNewUser(UserEntry user) {
        assertTrue(userService.saveNewEntry(user));
//        assertEquals(4, 2+2);
//        UserEntry user = userRepository.findByUserName("ram");
//        assertNotNull(!user.getJournalEntries().isEmpty());
    }

    @CsvSource({
            "1, 1, 2",
            "2, 10, 12",
    })
    @ParameterizedTest
    public void test(int a, int b, int expected) {
        assertEquals(expected, a+b);
    }
}