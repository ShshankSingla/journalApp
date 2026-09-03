package net.engineeringdigest.journalApp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal") // add mapping
@Tag(name = "Journal APIs" )
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    public JournalEntryControllerV2(JournalEntryService journalEntryService) {
        this.journalEntryService = journalEntryService;
    }

    @GetMapping // pehle hm user name path se le rahe theh, par ab security cotext se le rahe hai  : "/{userName}"
    @Operation(summary = "Get all journal entries of a user")
    public ResponseEntity<?> getAllJorunalEntreisOfUser(){ // @PathVariable String userName
        // yaha se hm user name fetch karnenge - security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        // ye normal code hai
        User user = userService.findByUserName(userName);

        List<JournalEntry> all = user.getJournalEntries();
        if(all != null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
//    @GetMapping
//    public List<JournalEntry> getAll(){
//        return journalEntryService.getAll();
//
//    }

    @PostMapping//("/{userName}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry ){ // no need of this anymore : @PathVariable String userName


        try{
            // get username from security context
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();

            myEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(myEntry,userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);

        }catch (Exception e){
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }

    }
//    @PostMapping
//    public JournalEntry createEntry(@RequestBody JournalEntry myEntry){
//        myEntry.setDate(LocalDateTime.now());
//        journalEntryService.saveEntry(myEntry);
//        return  myEntry;
//    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable String myId) { // normally we are using this @PathVariable ObjectId myId, but need to made changes for swagger
        ObjectId objectId = new ObjectId(myId);
        // till now we have userId

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        // now we have username also;

        User user = userService.findByUserName(userName);
//      user ki jitni bhi journal entries hai, usme find kar rahe hai ki myId hai ya nahi
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x->x.getId().equals(objectId)).collect(Collectors.toList());
        if(collect != null){
            Optional<JournalEntry> journalEntryOptional = journalEntryService.findById(objectId);

            if (journalEntryOptional.isPresent()) {
                return new ResponseEntity<>(journalEntryOptional.get(), HttpStatus.OK);
            }
        }
        // user nikala, user ke journal entries ki list ayi hogi, usme dekha ki respective id hai ya nahi, agar hai to uski shakal de do varna nahi 123



        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    /*
    @GetMapping("id/{myId}")
    public JournalEntry getJournalEntryById(@PathVariable ObjectId myId){
        return journalEntryService.findById(myId).orElse(null);
    }
     */


    @DeleteMapping("id/{myId}")// earlier id/{userName}/{myId}
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        boolean removed = journalEntryService.deleteById(myId,userName);
        if(removed){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
//        Optional<JournalEntry> entry = journalEntryService.findById(myId);
//
//        if(entry.isPresent()){
//            journalEntryService.deleteById(myId,userName);
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }


    }

    /*
    @DeleteMapping("id/{myId}")
    public boolean deleteJournalEntryById(@PathVariable ObjectId myId){

        journalEntryService.deleteById(myId);
        return true;
    }

     */


    @PutMapping("id/{id}")// earlier id/{userName}/{id}
    public ResponseEntity<JournalEntry> updateJournalEntryById(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User user = userService.findByUserName(userName);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x->x.getId().equals(id)).collect(Collectors.toList());

        // agar empty nahi hai , sahi user and usne sahi id di hai update karne ke liye
        if(collect != null){
            Optional<JournalEntry> journalEntryOptional = journalEntryService.findById(id);

            if (journalEntryOptional.isPresent()) {
                JournalEntry old = journalEntryOptional.get();
                old.setTitle( newEntry.getTitle() != null && !newEntry.getTitle().equals("")? newEntry.getTitle() : old.getTitle());
                old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
                journalEntryService.saveEntry(old);
                return new ResponseEntity<>(old, HttpStatus.OK);
            }
        }

        // under till lec 20
//        JournalEntry old = journalEntryService.findById(id).orElse(null);
//        if(old != null){
//            old.setTitle( newEntry.getTitle() != null && !newEntry.getTitle().equals("")? newEntry.getTitle() : old.getTitle());
//            old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
//            journalEntryService.saveEntry(old);
//            return new ResponseEntity<>(old, HttpStatus.OK);
//        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
//    @PutMapping("id/{id}")
//    public JournalEntry updateJournalEntryById(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry){
//        JournalEntry old = journalEntryService.findById(id).orElse(null);
//        if(old != null){
//            old.setTitle( newEntry.getTitle() != null && !newEntry.getTitle().equals("")? newEntry.getTitle() : old.getTitle());
//            old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
//        }
//        journalEntryService.saveEntry(old);
//        return old;
//    }

    // lec42 get all journal entries of every user through admin
    @GetMapping("/admin/all")
    public ResponseEntity<?> getAllJournalEntries() {
        return new ResponseEntity<>(
                journalEntryService.getAll(),
                HttpStatus.OK
        );
    }


}

