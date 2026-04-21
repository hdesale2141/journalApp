package com.hdworkspace.journalApp.Service;

import com.hdworkspace.journalApp.Entity.JournalEntry;
import com.hdworkspace.journalApp.Entity.User;
import com.hdworkspace.journalApp.Repository.JournalEntryRepo;
import com.hdworkspace.journalApp.Repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JournalEntryRepo journalEntryRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean saveUser(User userdetail){
        try {
            userdetail.setPassword(passwordEncoder.encode(userdetail.getPassword()));
            userdetail.setRole(User.Role.USER);
            userRepository.save(userdetail);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    public List<User> getAll(){
        return userRepository.findAll();
    }
    public Optional<User> findById(ObjectId id){
        return userRepository.findById(id);
    }
    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

    public void deleteEntries(String username){
        User user = userRepository.findByUserName(username);
        List<JournalEntry> journalEntries= user.getJournalEntries();
        for (JournalEntry journalEntry : journalEntries) {
            journalEntryRepo.delete(journalEntry);
        }
        userRepository.deleteByUserName(username);
    }
}
