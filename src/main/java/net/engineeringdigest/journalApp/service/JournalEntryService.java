package net.engineeringdigest.journalApp.service;


import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//business logic
@Service// scanner can scan this
public class JournalEntryService {
     @Autowired// dependency injection
     private JournalEntryRepository journalEntryRepository;

     @Autowired
     private UserService userService;

     // create methods
     @Transactional
     public void saveEntry(JournalEntry journalEntry, String userName){ // Body, username
          try {
               User user = userService.findByUserName(userName);  // find Ram123

               journalEntryRepository.save(journalEntry);  // save the body in db
               JournalEntry saved = journalEntryRepository.save(journalEntry);
               user.getJournalEntries().add(saved);
               // user.setUserName(null); // this will cause error that's why we are using @Transaction
               userService.saveUser(user);

          }catch (Exception e){
               System.out.println(e);
               throw new RuntimeException("An erro occured while saving the entry.", e);
          }


     }

     // overloaded method for save Entry for updating withour username
     public void saveEntry(JournalEntry journalEntry){ // Body, username
          journalEntryRepository.save(journalEntry);
     }

     public List<JournalEntry> getAll(){
          return journalEntryRepository.findAll();
     }

     public Optional<JournalEntry> findById(ObjectId id){
          return journalEntryRepository.findById(id);
     }

     @Transactional
     public boolean deleteById(ObjectId id, String userName) {
          boolean removed = false;
          try {
               System.out.println("Deleting Journal Entry: " + id);

               User user = userService.findByUserName(userName);
               removed = user.getJournalEntries().removeIf(
                       x -> x.getId().equals(id)
               ); // delete from both collcions

               if(removed) {
                    userService.saveUser(user);
                    journalEntryRepository.deleteById(id);
               }

          } catch (Exception e) {
               System.out.println(e);
               throw new RuntimeException("An error occurred while deleting  the entry: ",e);
          }
          return removed;
     }


}
//controller ---> service ---> repository