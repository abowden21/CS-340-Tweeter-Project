package edu.byu.cs.tweeter.client.model.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.FollowServiceProxy;
import edu.byu.cs.tweeter.shared.model.domain.AuthToken;
import edu.byu.cs.tweeter.shared.model.domain.User;
import edu.byu.cs.tweeter.shared.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.model.request.FollowRequest;
import edu.byu.cs.tweeter.shared.model.request.FollowStatusRequest;
import edu.byu.cs.tweeter.shared.model.request.UserFollowCountRequest;
import edu.byu.cs.tweeter.shared.model.response.FollowResponse;
import edu.byu.cs.tweeter.shared.model.response.FollowStatusResponse;
import edu.byu.cs.tweeter.shared.model.response.UserFollowCountResponse;

public class FollowServiceIntegrationTest {

    private FollowRequest validFollowRequest;
    private FollowRequest invalidFollowRequest;
    private FollowResponse successFollowResponse;
    private FollowResponse failureFollowResponse;

    private FollowRequest validUnfollowRequest;
    private FollowRequest invalidUnfollowRequest;
    private FollowResponse successUnfollowResponse;
    private FollowResponse failureUnfollowResponse;

    private FollowStatusRequest validFollowStatusRequest;
    private FollowStatusRequest invalidFollowStatusRequest;
    private FollowStatusResponse successFollowStatusResponse;
    private FollowStatusResponse failureFollowStatusResponse;

    private UserFollowCountRequest validUserFollowCountRequest;
    private UserFollowCountRequest invalidUserFollowCountRequest;
    private UserFollowCountResponse successUserFollowCountResponse;
    private UserFollowCountResponse failureUserFollowCountResponse;

    private FollowServiceProxy mFollowServiceProxy = new FollowServiceProxy();

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null);
        User otherUser =  new User("FirstName2", "LastName2", null);
        AuthToken authToken = new AuthToken("<mockToken>", "test");

        validFollowStatusRequest = new FollowStatusRequest(currentUser.getAlias(), otherUser.getAlias());
        invalidFollowStatusRequest = new FollowStatusRequest(null, null);

        validFollowRequest = new FollowRequest(authToken, true, currentUser.getAlias(), otherUser.getAlias());
        invalidFollowRequest = new FollowRequest(authToken, true, null, null);

        validUnfollowRequest = new FollowRequest(authToken, false, currentUser.getAlias(), otherUser.getAlias());
        invalidUnfollowRequest = new FollowRequest(authToken, false, null, null);

        validUserFollowCountRequest = new UserFollowCountRequest(otherUser.getAlias());
        invalidUserFollowCountRequest = new UserFollowCountRequest(null);

        successFollowStatusResponse = new FollowStatusResponse(true, true);
        successFollowResponse = new FollowResponse(true, true);
        successUnfollowResponse = new FollowResponse(true, false);
        successUserFollowCountResponse = new UserFollowCountResponse(1, 0);

        failureFollowStatusResponse = new FollowStatusResponse(false, "invalid input");
        failureFollowResponse = new FollowResponse(false, "invalid input");
        failureUnfollowResponse = new FollowResponse(false, "invalid input");
        failureUserFollowCountResponse = new UserFollowCountResponse("invalid input");
    }

    @Test
    public void testSetFollow_validFollowRequest_correctResponse() throws IOException, TweeterRemoteException {
        FollowResponse response = mFollowServiceProxy.setFollow(validFollowRequest);
        Assertions.assertEquals(successFollowResponse, response);
    }

    @Test
    public void testGetFollowStatus_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        FollowStatusResponse response = mFollowServiceProxy.getFollowStatus(validFollowStatusRequest);
        Assertions.assertEquals(successFollowStatusResponse, response);
    }

    @Test
    public void testGetUserFollowCount_validUserFollowCountRequest_correctResponse() throws IOException, TweeterRemoteException {
        UserFollowCountResponse response = mFollowServiceProxy.getUserFollowCount(validUserFollowCountRequest);
        Assertions.assertEquals(successUserFollowCountResponse, response);
    }

    @Test
    public void testSetFollow_validUnfollowRequest_correctResponse() throws IOException, TweeterRemoteException {
        FollowResponse response = mFollowServiceProxy.setFollow(validUnfollowRequest);
        Assertions.assertEquals(successUnfollowResponse, response);
    }
}
