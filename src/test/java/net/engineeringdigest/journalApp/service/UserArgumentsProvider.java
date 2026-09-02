package net.engineeringdigest.journalApp.service;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import net.engineeringdigest.journalApp.entity.User;

import java.util.stream.Stream;

public class UserArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of(User.builder().userName("user_" + System.currentTimeMillis()).password("123").build()),
                Arguments.of(User.builder().userName("user_" + (System.currentTimeMillis() + 1)).password("123").build())

        );
    }
}
