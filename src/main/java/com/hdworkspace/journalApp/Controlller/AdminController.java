package com.hdworkspace.journalApp.Controlller;

import com.hdworkspace.journalApp.Entity.JournalEntry;
import com.hdworkspace.journalApp.Entity.User;
import com.hdworkspace.journalApp.Service.JournalEntryService;
import com.hdworkspace.journalApp.Service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private JournalEntryService journalEntryService;
     private User user;
    private String userName;
    public void setAuthUsername(String userName){
        this.userName = userName;
    }
    @PostMapping("/entry/{username}")
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry myEntry, @PathVariable String username){
        try{
            user = userService.findByUserName(userName);
            if(user.getRole()==User.Role.USER){
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Only admin allow...!");
            }
            journalEntryService.saveEntry(myEntry, username);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("id/{userName}/{myId}")
    public ResponseEntity<?> deleteEntryById(@PathVariable ObjectId myId, @PathVariable String username){
        user = userService.findByUserName(userName);
        if(user.getRole()==User.Role.USER){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Only admin allow...!");
        }
        journalEntryService.deleteById(myId,userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/{username}")
    public ResponseEntity<?> updateUser(@RequestBody User user,@PathVariable String username){
        user = userService.findByUserName(userName);
        if(user.getRole()==User.Role.USER){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Only admin allow...!");
        }
        if(user != null){
            user.setUserName(user.getUserName());
            user.setPassword(user.getPassword());
            userService.saveUser(user);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteByUser(@PathVariable String username){
        try{
            user = userService.findByUserName(userName);
            if(user.getRole()== User.Role.USER){
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Only admin allow...!");
            }
            if(userService.findByUserName(username) == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found by the given username "+username);
            }
            userService.deleteEntries(username);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("id/{userName}/{myId}")
    public ResponseEntity<?> updateEntryById(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry, @PathVariable String userName)
    {
        JournalEntry old = journalEntryService.findById(myId).orElse(null);
        if(old != null){
            old.setTitle(!newEntry.getTitle().equals("") ?newEntry.getTitle():old.getTitle());
            old.setContent(newEntry.getContent() != null && !newEntry.getTitle().equals("")?newEntry.getContent():old.getContent());
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(old,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
