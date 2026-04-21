package com.hdworkspace.journalApp.Repository;

import com.hdworkspace.journalApp.Entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

//@Component
@Repository
public interface JournalEntryRepo extends MongoRepository<JournalEntry, ObjectId> {
}
