package edu.byu.cs.tweeter.shared.model.request;

import java.time.LocalDateTime;

public class StoryRequest {

    private final String userAlias;
    private final int limit;
    private final LocalDateTime lastTimestamp;

    public StoryRequest(String followerAlias, int limit, LocalDateTime lastTimestamp) {
        this.userAlias = followerAlias;
        this.limit = limit;
        this.lastTimestamp = lastTimestamp;
    }

    public String getUserAlias() {
        return userAlias;
    }

    public LocalDateTime getLastTimestamp() {
        return lastTimestamp;
    }

    public int getLimit() {
        return limit;
    }
}
