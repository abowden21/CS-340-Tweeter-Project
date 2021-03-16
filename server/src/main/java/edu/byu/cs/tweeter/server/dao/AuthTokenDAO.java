package edu.byu.cs.tweeter.server.dao;
import edu.byu.cs.tweeter.shared.model.domain.AuthToken;

public class AuthTokenDAO {
    public AuthTokenDAO() {}

    public void addAuthToken(AuthToken authToken) {}
    public AuthToken getAuthToken(String token) {
        return new AuthToken("<DummyToken>", "fakeAlias");
    }
    public void updateAuthToken(String token, AuthToken authToken) {}
    public void deleteAuthToken(String token) {}
}
