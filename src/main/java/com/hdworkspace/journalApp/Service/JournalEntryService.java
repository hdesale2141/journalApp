package com.hdworkspace.journalApp.Service;

import com.hdworkspace.journalApp.Entity.JournalEntry;
import com.hdworkspace.journalApp.Entity.User;
import com.hdworkspace.journalApp.Repository.JournalEntryRepo;
import com.hdworkspace.journalApp.Repository.UserRepository;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepo journalEntryRepo;
    @Autowired
    private UserRepository userRepository;
//    @Transactional
    public void saveEntry(@NonNull JournalEntry journalEntry, String username){

            User user = userRepository.findByUserName(username);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepo.save(journalEntry);

            user.getJournalEntries().add(saved);
            userRepository.save(user);
    }

    public void saveEntry(JournalEntry journalEntry){

        journalEntryRepo.save(journalEntry);
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepo.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepo.findById(id);
    }

    public void deleteById(ObjectId id, String userName){
        User user = userRepository.findByUserName(userName);
        user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        userRepository.save(user);
        journalEntryRepo.deleteById(id);
    }

}
