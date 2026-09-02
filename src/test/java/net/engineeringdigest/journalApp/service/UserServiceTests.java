package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

//    @Disabled
//    @Test
//    public void testFindByUserName(){
////        assertEquals(4,2+2);
//        User user = userRepository.findByUserName("ram");
//        assertTrue(!user.getJournalEntries().isEmpty());
//    }

//    @ParameterizedTest
//    @ValueSource(strings= {
//        "ram","shyam", "vipul"
//    })
//    public void testFindByUserName(String name ){
//        assertNotNull(userRepository.findByUserName(name), "failer for: "+name);
//    }

    @ParameterizedTest
    @ArgumentsSource(UserArgumentsProvider.class)
    public void testSaveNewUser(User user ){
        assertTrue(userService.saveNewUser(user));
    }

//    @ParameterizedTest
//    @CsvSource(
//            {
//                    "1,1,2",
//                    "2,10,12",
//                    "3,3,9"
//            }
//    )
//    public void test(int a, int b, int expected){
//        assertEquals(expected, a+b);
//    }
}
