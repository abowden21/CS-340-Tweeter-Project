package edu.byu.cs.tweeter.model.service.response;

import edu.byu.cs.tweeter.model.domain.User;

public class GetUserResponse {
    private User retrievedUser;

    public GetUserResponse(User retrievedUser) {
        this.retrievedUser = retrievedUser;
    }

    public User getRetrievedUser() {
        return retrievedUser;
    }
}
