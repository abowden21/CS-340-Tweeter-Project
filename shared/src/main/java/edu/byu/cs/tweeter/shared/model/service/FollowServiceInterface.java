package edu.byu.cs.tweeter.shared.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.shared.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.model.request.FollowRequest;
import edu.byu.cs.tweeter.shared.model.request.FollowStatusRequest;
import edu.byu.cs.tweeter.shared.model.request.UserFollowCountRequest;
import edu.byu.cs.tweeter.shared.model.response.FollowResponse;
import edu.byu.cs.tweeter.shared.model.response.FollowStatusResponse;
import edu.byu.cs.tweeter.shared.model.response.UserFollowCountResponse;

public interface FollowServiceInterface {
    FollowResponse setFollow(FollowRequest request) throws IOException, TweeterRemoteException;

    FollowStatusResponse getFollowStatus(FollowStatusRequest request) throws IOException, TweeterRemoteException;

    UserFollowCountResponse getUserFollowCount(UserFollowCountRequest request) throws IOException, TweeterRemoteException;
}
