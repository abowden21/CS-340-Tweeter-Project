package edu.byu.cs.tweeter.shared.model.domain;

import java.io.Serializable;

/**
 * Represents an auth token in the system.
 */
public class AuthToken implements Serializable {
    private String token;

    public AuthToken() {}

    public AuthToken(String token) {
        this.token = token;
    }

    private String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
