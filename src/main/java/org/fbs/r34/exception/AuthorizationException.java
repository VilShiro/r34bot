package org.fbs.r34.exception;

import com.pengrad.telegrambot.model.User;
import lombok.Getter;

@Getter
public class AuthorizationException extends RuntimeException {
    private final User user;

    public AuthorizationException(User user) {
        super("Unauthorized attempt to obtain data from: " + user);
        this.user = user;
    }
}
