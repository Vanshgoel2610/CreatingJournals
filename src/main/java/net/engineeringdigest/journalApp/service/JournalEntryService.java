package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.UserEntry;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void saveEntry(JournalEntry journalEntity, String userName) {
        try {
            UserEntry user = userService.findByUserName(userName);
            journalEntity.setDate(LocalDateTime.now());
            JournalEntry save = journalEntryRepository.save(journalEntity);
            user.getJournalEntries().add(save);
            userService.saveEntry(user);
        } catch(Exception e) {
            System.out.println(e);
            throw new RuntimeException("An error occured");
        }
    }
    public void saveEntry(JournalEntry journalEntity) {
        journalEntryRepository.save(journalEntity);
    }
    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }
    public Optional<JournalEntry> findById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }
    @Transactional
    public boolean deleteById(ObjectId id, String userName) {
        boolean removed = false;
        try {
            UserEntry user = userService.findByUserName(userName);
            removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if(removed) {
                userService.saveEntry(user);
                journalEntryRepository.deleteById(id);
            }
        } catch(Exception e) {
            System.out.println(e);
            throw new RuntimeException("An error occurred while deleting", e);
        }
        return removed;
    }

    public UserEntry findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }
}