package edu.byu.cs.tweeter.shared.model.response;

import java.util.Objects;

public class FollowResponse extends Response {
    private boolean followResponse;

    public FollowResponse() {}

    public FollowResponse(boolean success, boolean followResponse) {
        super(success);
        this.followResponse = followResponse;
    }

    public FollowResponse(boolean success, String message) {
        super(success, message);
    }

    public boolean isFollowResponse() {
        return followResponse;
    }

    public void setFollowResponse(boolean followResponse) {
        this.followResponse = followResponse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FollowResponse that = (FollowResponse) o;
        return followResponse == that.followResponse && isSuccess() == that.isSuccess()
                && getMessage() == that.getMessage();
    }
}
