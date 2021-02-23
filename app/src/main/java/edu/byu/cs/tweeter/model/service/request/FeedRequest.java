package edu.byu.cs.tweeter.model.service.request;

import java.time.LocalDateTime;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class FeedRequest {

    private final AuthToken authToken;
    private final int limit;
    private final LocalDateTime lastTimestamp;

    public FeedRequest(AuthToken authToken, int limit, LocalDateTime lastTimestamp) {
        this.authToken = authToken;
        this.limit = limit;
        this.lastTimestamp = lastTimestamp;
    }

    public AuthToken getAuthToken() {
        return this.authToken;
    }

    public LocalDateTime getLastTimestamp() {
        return lastTimestamp;
    }

    public int getLimit() {
        return limit;
    }
}
