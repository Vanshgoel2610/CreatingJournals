package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.UserEntry;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

//@Component
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveEntry(UserEntry user) {
        userRepository.save(user);
    }

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public boolean saveNewEntry(UserEntry user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USERS"));
            userRepository.save(user);
            return true;
        } catch(Exception e) {
            logger.trace("Tracing");
            logger.debug("Debugging");
            logger.info("Random MSG");
            logger.warn("Warning");
            logger.error("Displaying Error");
            System.out.println(e);
            return false;
        }
    }

    public void saveNewAdmin(UserEntry user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USERS"));
        userRepository.save(user);
    }
    public List<UserEntry> getAll() {
        return userRepository.findAll();
    }
    public Optional<UserEntry> findById(ObjectId id) {
        return userRepository.findById(id);
    }
    public void deleteById(ObjectId id) {
        userRepository.deleteById(id);
    }
    public UserEntry findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }
}