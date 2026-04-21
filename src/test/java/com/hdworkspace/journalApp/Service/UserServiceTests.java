package com.hdworkspace.journalApp.Service;

import com.hdworkspace.journalApp.Entity.User;
import com.hdworkspace.journalApp.Repository.UserRepository;
import org.junit.jupiter.api.*;
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

    @BeforeAll
    public static void beforeAllStarts(){
        System.out.println("All Test Cases Are Starting....!");
    }

    @BeforeEach
    public void beforeEach(){
        System.out.println("This Test is Starting.....");
    }

    @AfterAll
    public static void afterAllEnds(){
        System.out.println("All tests are finished....!");
    }

    @AfterEach
    public void afterEachTest(){
        System.out.println("This Test is Finished....");
    }


    @ParameterizedTest
    @ArgumentsSource(UserArgumentsProvider.class)
    public void testCreateUser(User user){
        assertTrue(userService.saveUser(user));
    }

    @Disabled
    @ParameterizedTest
    @CsvSource({
            "Suresh",
            "paresh"
    })
    public void testFindByUserName(String username){
//        assertEquals(4,3+1);
        User user = userRepository.findByUserName(username);
        assertFalse(user.getJournalEntries().isEmpty());
    }

    @Disabled
    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "5,3,8",
            "15,4,8",
            "34,65,99"
    })
    public void addTest(int a, int b, int expected){
        assertEquals(expected,a+b);
    }
}
