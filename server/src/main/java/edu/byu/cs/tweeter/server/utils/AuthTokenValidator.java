package edu.byu.cs.tweeter.server.utils;

import java.time.LocalDateTime;

import edu.byu.cs.tweeter.server.DataAccessException;
import edu.byu.cs.tweeter.server.dao.AuthTokenDAO;
import edu.byu.cs.tweeter.shared.model.domain.AuthToken;

public class AuthTokenValidator {

    private static String failedAuthTokenInvalidMessage = "Client Error: Failed to send status; auth token invalid.";

    public static void checkAuthToken(AuthToken authToken) throws DataAccessException {
        // Validate authToken, get user
        AuthTokenDAO authTokenDAO = new AuthTokenDAO();
        if (authToken.getExpirationDateTime().isBefore(LocalDateTime.now())) {
            // AuthToken has expired; delete it and return a failed response.
            authTokenDAO.deleteAuthToken(authToken.getToken());
            throw new RuntimeException(failedAuthTokenInvalidMessage);
        }
    }
}
