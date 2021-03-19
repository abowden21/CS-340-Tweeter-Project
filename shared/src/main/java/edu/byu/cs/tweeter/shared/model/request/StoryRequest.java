package edu.byu.cs.tweeter.shared.model.request;

import java.time.LocalDateTime;

public class StoryRequest {

    private final String userAlias;
    private final int limit;
    //private final LocalDateTime lastTimestamp;
    private final String timestampString;

    public StoryRequest() {
        userAlias = "";
        limit = 10;
        timestampString = LocalDateTime.now().toString();
    }

    public StoryRequest(String followerAlias, int limit, LocalDateTime lastTimestamp) {
        this.userAlias = followerAlias;
        this.limit = limit;
        if (lastTimestamp != null) {
            this.timestampString = lastTimestamp.toString();
        }
        else {
            this.timestampString = LocalDateTime.now().toString();
        }
    }

    public StoryRequest(String followerAlias, int limit, String timestampString) {
        this.userAlias = followerAlias;
        this.limit = limit;
        this.timestampString = timestampString;
    }



    public String getUserAlias() {
        return userAlias;
    }

    public LocalDateTime getLastTimestamp() {
        return LocalDateTime.parse(this.timestampString);
    }

    public String getTimestampString() {
        return this.timestampString;
    }



    public int getLimit() {
        return limit;
    }
}
