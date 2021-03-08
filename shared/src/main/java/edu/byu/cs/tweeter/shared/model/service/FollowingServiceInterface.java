package edu.byu.cs.tweeter.shared.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.shared.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.model.request.FollowingRequest;
import edu.byu.cs.tweeter.shared.model.response.FollowingResponse;

public interface FollowingServiceInterface {
    FollowingResponse getFollowees(FollowingRequest request) throws IOException, TweeterRemoteException;
}
