package edu.byu.cs.tweeter.shared.model.request;

import java.time.LocalDateTime;

import edu.byu.cs.tweeter.shared.model.domain.AuthToken;

public class FeedRequest {

    private final AuthToken authToken;
    private final int limit;
    //private final LocalDateTime lastTimestamp;
    private final String timestampString;

    public FeedRequest() {
        authToken = new AuthToken();
        limit = 10;
        timestampString = LocalDateTime.now().toString();
    }

    public FeedRequest(AuthToken authToken, int limit, LocalDateTime lastTimestamp) {
        this.authToken = authToken;
        this.limit = limit;
        if (lastTimestamp != null) {
            this.timestampString = lastTimestamp.toString();
        }
        else {
            this.timestampString = LocalDateTime.now().toString();
        }

    }

    public AuthToken getAuthToken() {
        return this.authToken;
    }

    public LocalDateTime getLastTimestamp() {
        return LocalDateTime.parse(timestampString);
    }

    public String getIimestampString() {
        return timestampString;
    }

    public int getLimit() {
        return limit;
    }
}
