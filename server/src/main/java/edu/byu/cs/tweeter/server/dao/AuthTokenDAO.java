package edu.byu.cs.tweeter.server.dao;
import edu.byu.cs.tweeter.server.DataAccessException;
import edu.byu.cs.tweeter.shared.model.domain.AuthToken;

public class AuthTokenDAO {
    public AuthTokenDAO() {}

    public void addAuthToken(AuthToken authToken) throws DataAccessException {}
    public AuthToken getAuthToken(String token) throws DataAccessException {
        return new AuthToken("<DummyToken>", "fakeAlias");
    }
    public void updateAuthToken(String token, AuthToken authToken) throws DataAccessException {}
    public void deleteAuthToken(String token) throws DataAccessException {}
}
