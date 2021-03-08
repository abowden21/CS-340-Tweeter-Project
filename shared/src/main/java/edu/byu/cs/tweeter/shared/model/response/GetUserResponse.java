package edu.byu.cs.tweeter.shared.model.response;

import edu.byu.cs.tweeter.shared.model.domain.User;

public class GetUserResponse {
    private User retrievedUser;

    public GetUserResponse(User retrievedUser) {
        this.retrievedUser = retrievedUser;
    }

    public User getRetrievedUser() {
        return retrievedUser;
    }
}
