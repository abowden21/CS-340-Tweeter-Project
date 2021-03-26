package edu.byu.cs.tweeter.shared.model.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents an auth token in the system.
 */
public class AuthToken implements Serializable {
    private String token;
    private String userAlias;
    private String expiration;

    public AuthToken() {
        this.setExpirationDateTime(LocalDateTime.now().plusDays(10));
    }

    public AuthToken(String token, String userAlias) {
        this.token = token;
        this.userAlias = userAlias;
        this.setExpirationDateTime(LocalDateTime.now().plusDays(10));
    }

    public AuthToken(String token, String userAlias, String expiration) {
        this.token = token;
        this.userAlias = userAlias;
        this.expiration = expiration;
    }

    public AuthToken(String token, String userAlias, int daysValid) {
        this.token = token;
        this.userAlias = userAlias;
        this.setExpirationDateTime(LocalDateTime.now().plusDays(daysValid));
    }

    public String getToken() {
        return this.token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    public String getUserAlias() {
        return this.userAlias;
    }
    public void setUserAlias(String userAlias) {
        this.userAlias = userAlias;
    }

    public String getExpiration() {
        return expiration;
    }
    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public LocalDateTime getExpirationDateTime() {
        return LocalDateTime.parse(this.expiration);
    }
    public void setExpirationDateTime(LocalDateTime dateTime) {
        this.expiration = dateTime.toString();
    }
}
