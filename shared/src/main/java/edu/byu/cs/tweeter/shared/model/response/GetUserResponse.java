package edu.byu.cs.tweeter.shared.model.response;

import edu.byu.cs.tweeter.shared.model.domain.User;

public class GetUserResponse extends Response {
    private User retrievedUser;

    public GetUserResponse() {}
    public GetUserResponse(String message) {
        super(false, message);
    }
    public GetUserResponse(User retrievedUser) {
        this.retrievedUser = retrievedUser;
    }

    public User getRetrievedUser() {
        return retrievedUser;
    }
    public void setRetrievedUser(User user) {
        this.retrievedUser = user;
    }
}
