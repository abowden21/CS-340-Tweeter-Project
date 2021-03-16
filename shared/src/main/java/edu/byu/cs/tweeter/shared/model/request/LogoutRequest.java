package edu.byu.cs.tweeter.shared.model.request;

import edu.byu.cs.tweeter.shared.model.domain.AuthToken;

public class LogoutRequest {
    private AuthToken authToken;

    public LogoutRequest(){}

    public LogoutRequest(AuthToken authToken) {
        this.authToken = authToken;
    }

    public AuthToken getAuthToken() {
        return this.authToken;
    }
    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }
}
