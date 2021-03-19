package edu.byu.cs.tweeter.shared.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.shared.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.model.request.FollowersRequest;
import edu.byu.cs.tweeter.shared.model.response.FollowersResponse;

public interface FollowersServiceInterface {
    FollowersResponse getFollowers(FollowersRequest request) throws IOException, TweeterRemoteException;
}
