package edu.byu.cs.tweeter.shared.model.request;

public class FollowStatusRequest {
    private String loggedInUserAlias;
    private String otherUserAlias;

    public FollowStatusRequest() {}

    public FollowStatusRequest(String loggedInUserAlias, String otherUserAlias) {
        this.loggedInUserAlias = loggedInUserAlias;
        this.otherUserAlias = otherUserAlias;
    }

    public String getLoggedInUserAlias() {
        return loggedInUserAlias;
    }

    public String getOtherUserAlias() {
        return otherUserAlias;
    }

    public void setLoggedInUserAlias(String loggedInUserAlias) {
        this.loggedInUserAlias = loggedInUserAlias;
    }

    public void setOtherUserAlias(String otherUserAlias) {
        this.otherUserAlias = otherUserAlias;
    }
}
