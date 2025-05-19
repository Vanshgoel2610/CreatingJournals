package net.engineeringdigest.journalApp.scheduler;

import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.UserEntry;
import net.engineeringdigest.journalApp.enums.Sentiments;
import net.engineeringdigest.journalApp.repository.UserRepositoryImpl;
import net.engineeringdigest.journalApp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;
    @Autowired
    private UserRepositoryImpl userRepository;
    @Autowired
    private AppCache appCache;

    @Scheduled(cron = "0 0/1 * 1/1 * ?")
    public void fetchUsersAndSendSaMail() {
        List<UserEntry> users = userRepository.getUserForSA();
        for (UserEntry user : users) {
            List<JournalEntry> journalEntries = user.getJournalEntries();
            HashMap<Sentiments, Integer> sentimentCounts = new HashMap<>();
            List<Sentiments> sentiments = journalEntries.stream().filter(x -> x.getDate()
                                        .isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS)))
                                        .map(x -> x.getSentiments()).collect(Collectors.toList());

            for (Sentiments sentiment : sentiments) {
                if (sentiment != null) {
                    sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0) + 1);
                }
            }

            Sentiments mostFrequentSentiment = null;
            int maxCount = 0;

            for (Map.Entry<Sentiments, Integer> entry : sentimentCounts.entrySet()) {
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }
            if (mostFrequentSentiment != null) {
//                SentimentData sentimentData = SentimentData.builder().email(user.getEmail()).sentiment("Sentiment for last 7 days " + mostFrequentSentiment).build();
//                try{
//                    kafkaTemplate.send("weekly-sentiments", sentimentData.getEmail(), sentimentData);
//                }catch (Exception e){
                    emailService.sendEmail(user.getEmail(), "Sentiment for previous week", mostFrequentSentiment.toString());
//                }
            }
        }
    }

    @Scheduled(cron = "0 0/10 * ? * *")
    public void clearAppCache() {
        appCache.init();
    }
}