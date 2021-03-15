package edu.byu.cs.tweeter.shared.model.domain;

import java.io.Serializable;

/**
 * Represents an auth token in the system.
 */
public class AuthToken implements Serializable {
    private String token;
    private String userAlias;
    // TODO: Timestamp expires

    public AuthToken() {}

    public AuthToken(String token, String userAlias) {
        this.token = token;
        this.userAlias = userAlias;
    }

    public String getToken() {
        return this.token;
    }
    public String getUserAlias() {
        return this.userAlias;
    }

    public void setToken(String token) {
        this.token = token;
    }
    public void setUserAlias(String userAlias) {
        this.userAlias = userAlias;
    }
}
