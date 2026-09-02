package net.engineeringdigest.journalApp.service;


//import com.mongodb.DuplicateKeyException;
import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

//business logic
@Service// scanner can scan this
@Slf4j
public class UserService {
     @Autowired// dependency injection
     private UserRepository userRepository;

     private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

     // l-27 logging
     //use annotation or it
     //private static final Logger logger = LoggerFactory.getLogger(UserService.class);

     // create methods
     // save new user
     public boolean saveNewUser(User user) {

          log.info("saveNewUser() called");

          try {
               user.setPassword(passwordEncoder.encode(user.getPassword()));
               user.setRoles(Arrays.asList("USER"));

               log.info("Saving user {}", user.getUserName());

               userRepository.save(user);

               log.info("User saved");

               return true;

          } catch (Exception e) {
               //e.printStackTrace();  // now we are using logging
               log.warn("Username '{}' already exists", user.getUserName());
               return false;
          }
     }


     // lec 22
     public void saveAdmin(User user){
          user.setPassword(passwordEncoder.encode(user.getPassword()));
          user.setRoles(Arrays.asList("USER","ADMIN"));
          userRepository.save(user);
     }

     public void saveUser(User user){
          userRepository.save(user);
     }

     public List<User> getAll(){
          return userRepository.findAll();
     }

     public Optional<User> findById(ObjectId id){
          return userRepository.findById(id);
     }

     public void deleteById(ObjectId id){
          userRepository.deleteById(id);
     }

     public User findByUserName(String userName){
          return userRepository.findByUserName(userName);
     }


}
//controller ---> service ---> repository

/*
@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    public void saveNewUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("USER"));
        userRepository.save(user);
    }

    public void updateUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public Optional<User> findById(ObjectId id){
        return userRepository.findById(id);
    }

    public void deleteById(ObjectId id){
        userRepository.deleteById(id);
    }

    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }
}
 */