package com.hdworkspace.journalApp.Entity;

import jakarta.annotation.Nonnull;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users_entries")
@Getter
@Setter
@Builder
public class User {
    @Id
    private ObjectId id;
    @Indexed(unique = true)
    @NonNull
    private String userName;
    @Nonnull
    private String password;
    @DBRef
    List<JournalEntry> journalEntries = new ArrayList<>();
    private Role role;

    public enum Role{
        USER,
        ADMIN
    }


}
