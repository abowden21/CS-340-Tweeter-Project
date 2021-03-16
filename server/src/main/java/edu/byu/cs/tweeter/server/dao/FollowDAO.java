package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.shared.model.request.FollowRequest;
import edu.byu.cs.tweeter.shared.model.request.FollowStatusRequest;
import edu.byu.cs.tweeter.shared.model.request.UserFollowCountRequest;
import edu.byu.cs.tweeter.shared.model.response.FollowResponse;
import edu.byu.cs.tweeter.shared.model.response.FollowStatusResponse;
import edu.byu.cs.tweeter.shared.model.response.UserFollowCountResponse;

public class FollowDAO {

    public FollowResponse setFollow(FollowRequest request) {
        return new FollowResponse(true, true);
    }

    public FollowResponse setUnfollow(FollowRequest request) {
        return new FollowResponse(true, false);
    }

    public FollowStatusResponse getFollowStatus(FollowStatusRequest request) {
        return new FollowStatusResponse(true, true);
    }

    public UserFollowCountResponse getUserFollowCount(UserFollowCountRequest request) {
        return new UserFollowCountResponse(100, 99);
    }
}
