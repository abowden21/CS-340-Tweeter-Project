package edu.byu.cs.tweeter.shared.model.request;

import java.time.LocalDateTime;

import edu.byu.cs.tweeter.shared.model.domain.AuthToken;

public class FeedRequest {

    private AuthToken authToken;
    private int limit;
    //private final LocalDateTime lastTimestamp;
    private String timestampString;

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

    public FeedRequest(AuthToken authToken, int limit, String lastTimestamp) {
        this.authToken = authToken;
        this.limit = limit;
        if (lastTimestamp != null) {
            this.timestampString = lastTimestamp;
        }
//        else {
//            this.timestampString = LocalDateTime.now().toString();
//        }
    }

    public AuthToken getAuthToken() {
        return this.authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

    public LocalDateTime getLastTimestamp() {
        return LocalDateTime.parse(timestampString);
    }

    public String getTimestampString() {
        return timestampString;
    }

    public void setTimestampString(String timestampString) {
        this.timestampString = timestampString;
    }

    public void setTimestampString(LocalDateTime lastTimeStamp) {
        this.timestampString = lastTimeStamp.toString();
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
