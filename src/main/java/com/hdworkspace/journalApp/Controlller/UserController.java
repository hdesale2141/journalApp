package com.hdworkspace.journalApp.Controlller;

import com.hdworkspace.journalApp.Entity.JournalEntry;
import com.hdworkspace.journalApp.Entity.User;
import com.hdworkspace.journalApp.Service.JournalEntryService;
import com.hdworkspace.journalApp.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JournalEntryService journalEntryService;

//    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @GetMapping
    public List<User> getAll(){
        return  userService.getAll();
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user){
        try {
            userService.saveUser(user);
            return ResponseEntity.ok(user);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/get/{userName}")
    public ResponseEntity<?> getAllJournalEntriesByUser(@PathVariable String userName){
        try{
            User user = userService.findByUserName(userName);
            List<JournalEntry> all = user.getJournalEntries();
            if(all != null && !all.isEmpty()){
                return new ResponseEntity<>(all,HttpStatus.OK);
            }
            return new ResponseEntity<>("Empty Entries..!",HttpStatus.OK);
        }catch (Exception e){
            log.info("info");
            log.warn("warn");
            log.error("error occured for username {} [{}]",userName,e.getMessage());
            log.debug("debug");
            log.trace("trace");
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getEntryById(@PathVariable ObjectId myId){
        JournalEntry journalEntry = journalEntryService.findById(myId).orElse(null);
        if(journalEntry != null){
            return new ResponseEntity<>(journalEntry, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
