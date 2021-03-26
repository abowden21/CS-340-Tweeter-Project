package edu.byu.cs.tweeter.server.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.byu.cs.tweeter.server.DataAccessException;
import edu.byu.cs.tweeter.shared.model.domain.AuthToken;

public class AuthTokenDAOTests {

    AuthTokenDAO authTokenDAO;
    AuthToken testToken1;

    @BeforeEach
    void setup() {
        authTokenDAO = new AuthTokenDAO();
        testToken1 = new AuthToken("<test_token>", "no-user");
    }

    @Test
    void test_retrieveAuthToken() throws DataAccessException {
        authTokenDAO.getAuthToken(testToken1.getToken());
        // TODO test later
    }

    @Test
    void test_addAuthToken() throws DataAccessException {
        authTokenDAO.addAuthToken(testToken1);
    }
}
