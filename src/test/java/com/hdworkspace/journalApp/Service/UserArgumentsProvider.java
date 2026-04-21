package com.hdworkspace.journalApp.Service;

import com.hdworkspace.journalApp.Entity.User;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class UserArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of(User.builder().userName("Shreya").password("123").journalEntries(Collections.emptyList()).build()),
                Arguments.of(User.builder().userName("Arya").password("123").journalEntries(Collections.emptyList()).build())
        );
    }
}
