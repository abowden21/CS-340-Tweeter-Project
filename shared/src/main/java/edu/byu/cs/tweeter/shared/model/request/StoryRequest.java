package edu.byu.cs.tweeter.shared.model.request;

public class StoryRequest {

    private String userAlias;
    private int limit;
    private String lastTimestampString;

    public void setUserAlias(String userAlias) {
        this.userAlias = userAlias;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public StoryRequest() {
        userAlias = "";
        limit = 10;
    }

    public StoryRequest(String followerAlias, int limit, String lastTimestampString) {
        this.userAlias = followerAlias;
        this.limit = limit;
        this.lastTimestampString = lastTimestampString;
    }

    public String getUserAlias() {
        return userAlias;
    }

    public String getLastTimestampString() {
        return this.lastTimestampString;
    }

    public void setLastTimestampString(String lastTimestampString) {
        this.lastTimestampString = lastTimestampString;
    }



    public int getLimit() {
        return limit;
    }
}
