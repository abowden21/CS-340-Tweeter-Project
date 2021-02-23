package edu.byu.cs.tweeter.model.domain;

import java.io.Serializable;

/**
 * Represents an auth token in the system.
 */
public class AuthToken implements Serializable {
    private final String token;

    public AuthToken(String token) {
        this.token = token;
    }

    private String getToken() {
        return this.token;
    }
}
