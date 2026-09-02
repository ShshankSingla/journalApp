package net.engineeringdigest.journalApp.scheduler;


import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.enums.Sentiment;
import net.engineeringdigest.journalApp.model.SentimentData;
import net.engineeringdigest.journalApp.repository.UserRepositoryImpl;
import net.engineeringdigest.journalApp.service.EmailService;
//import net.engineeringdigest.journalApp.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.kafka.core.KafkaTemplate;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

//    @Autowired
//    private SentimentAnalysisService sentimentAnalysisService;

    @Autowired
    private AppCache appCache;

    @Autowired
    private KafkaTemplate<String, SentimentData> kafkaTemplate;

    // l-37
    // integrate both things - fetching user and sending emial
//    @Scheduled(cron = "0 0 9 * * SUN")// cron expression
//    public  void fetchUsersAndSendSAMail(){
//        List<User> users = userRepository.getUserForSA();
//        for(User user: users){
//            List<JournalEntry> journalEntries = user.getJournalEntries();
//            List<String> filteredEntries = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x->x.getContent()).collect(Collectors.toList());
//            // to join the strings into a single string
//            String entry = String.join(" ", filteredEntries);
//            String sentiment = sentimentAnalysisService.getSentiment(entry);
//            emailService.sendEmail(user.getEmail(), "Sentiment for last 7 days", sentiment);
//
//        }
//
//    }



    // l-38
    //@Scheduled(cron = "0 0 9 * * SUN") // every Sunday
//    @Scheduled(cron = "0 0/1 * ? * *") // every minute
    @Scheduled(fixedRate = 10000) // for testing use every 10 second
    public void fetchUsersAndSendSaMail() {
        List<User> users = userRepository.getUserForSA();
        for (User user : users) {
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment, Integer> sentimentCounts = new HashMap<>();
            for (Sentiment sentiment : sentiments) {
                if (sentiment != null)
                    sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0) + 1);
            }
            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;
            for (Map.Entry<Sentiment, Integer> entry : sentimentCounts.entrySet()) {
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }

//            // directly sending mail - we use kafka so no longer needed this
//            if(mostFrequentSentiment != null){
//                emailService.sendEmail(user.getEmail(),"Sentiment for last 7 days ",mostFrequentSentiment.toString());
//            }

            // lec 41
            if (mostFrequentSentiment != null) {
                SentimentData sentimentData = SentimentData.builder().email(user.getEmail()).sentiment("Sentiment for last 7 days " + mostFrequentSentiment).build();
                //kafkaTemplate.send("weekly-sentiments", sentimentData.getEmail(), sentimentData);
                // later
                try{
                    kafkaTemplate.send("weekly-sentiments", sentimentData.getEmail(), sentimentData);
                }catch (Exception e){
                    emailService.sendEmail(sentimentData.getEmail(), "Sentiment for previous week", sentimentData.getSentiment());
                }
            }
        }
    }

// temp logs for testing
//public void fetchUsersAndSendSaMail() {
//
//    List<User> users = userRepository.getUserForSA();
//
//    System.out.println("Users found: " + users.size());
//
//    for (User user : users) {
//
//        System.out.println("Username: " + user.getUserName());
//        System.out.println("Email: " + user.getEmail());
//
//        List<JournalEntry> journalEntries = user.getJournalEntries();
//
//        System.out.println("Total journal entries: " + journalEntries.size());
//
//        List<Sentiment> sentiments = journalEntries.stream()
//                .filter(x -> x.getDate() != null &&
//                        x.getDate().isAfter(
//                                LocalDateTime.now().minus(7, ChronoUnit.DAYS)))
//                .map(JournalEntry::getSentiment)
//                .collect(Collectors.toList());
//
//        System.out.println("Recent sentiments: " + sentiments);
//
//        Map<Sentiment, Integer> sentimentCounts = new HashMap<>();
//
//        for (Sentiment sentiment : sentiments) {
//            if (sentiment != null) {
//                sentimentCounts.put(
//                        sentiment,
//                        sentimentCounts.getOrDefault(sentiment, 0) + 1
//                );
//            }
//        }
//
//        Sentiment mostFrequentSentiment = null;
//        int maxCount = 0;
//
//        for (Map.Entry<Sentiment, Integer> entry : sentimentCounts.entrySet()) {
//            if (entry.getValue() > maxCount) {
//                maxCount = entry.getValue();
//                mostFrequentSentiment = entry.getKey();
//            }
//        }
//
//        System.out.println("Most frequent sentiment: " + mostFrequentSentiment);
//
//        if (mostFrequentSentiment != null) {
//
//            System.out.println(
//                    "Sending email to: " + user.getEmail()
//            );
//
//            emailService.sendEmail(
//                    user.getEmail(),
//                    "Sentiment for last 7 days",
//                    mostFrequentSentiment.toString()
//            );
//        } else {
//            System.out.println(
//                    "No sentiment found. Email NOT sent to: "
//                            + user.getEmail()
//            );
//        }
//    }
//}

    @Scheduled(cron ="0 0/10 * ? * SUN")
    public void clearAppCache(){
        appCache.init();
    }
}
