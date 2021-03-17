package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.server.dao.FollowDAO;
import edu.byu.cs.tweeter.shared.model.domain.AuthToken;
import edu.byu.cs.tweeter.shared.model.domain.User;
import edu.byu.cs.tweeter.shared.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.model.request.FollowRequest;
import edu.byu.cs.tweeter.shared.model.request.FollowStatusRequest;
import edu.byu.cs.tweeter.shared.model.request.UserFollowCountRequest;
import edu.byu.cs.tweeter.shared.model.response.FollowResponse;
import edu.byu.cs.tweeter.shared.model.response.FollowStatusResponse;
import edu.byu.cs.tweeter.shared.model.response.UserFollowCountResponse;

public class FollowServiceImplTest {

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

    private FollowServiceImpl mFollowServiceImplSpy;

    /**
     * Create a FollowingService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null);
        User otherUser =  new User("FirstName2", "LastName2", null);
        AuthToken authToken = new AuthToken("<mockToken>", "test");

        // Setup request objects to use in the tests
        validFollowStatusRequest = new FollowStatusRequest(currentUser.getAlias(), otherUser.getAlias());
        invalidFollowStatusRequest = new FollowStatusRequest(null, null);

        validFollowRequest = new FollowRequest(authToken, true, currentUser.getAlias(), otherUser.getAlias());
        invalidFollowRequest = new FollowRequest(authToken, true, null, null);

        validUnfollowRequest = new FollowRequest(authToken, false, currentUser.getAlias(), otherUser.getAlias());
        invalidUnfollowRequest = new FollowRequest(authToken, false, null, null);

        validUserFollowCountRequest = new UserFollowCountRequest("alias");
        invalidUserFollowCountRequest = new UserFollowCountRequest(null);

        // Setup a mock ServerFacade that will return known responses
        successFollowStatusResponse = new FollowStatusResponse(true, true);
        successFollowResponse = new FollowResponse(true, true);
        successUnfollowResponse = new FollowResponse(false, false);
        successUserFollowCountResponse = new UserFollowCountResponse(10, 9);

        FollowDAO mockFollowDAO = Mockito.mock(FollowDAO.class);
        Mockito.when(mockFollowDAO.getFollowStatus(validFollowStatusRequest)).thenReturn(successFollowStatusResponse);
        Mockito.when(mockFollowDAO.setFollow(validFollowRequest)).thenReturn(successFollowResponse);
        Mockito.when(mockFollowDAO.setUnfollow(validUnfollowRequest)).thenReturn(successUnfollowResponse);
        Mockito.when(mockFollowDAO.getUserFollowCount(validUserFollowCountRequest)).thenReturn(successUserFollowCountResponse);

        failureFollowStatusResponse = new FollowStatusResponse(false, "invalid input");
        failureFollowResponse = new FollowResponse(false, "invalid input");
        failureUnfollowResponse = new FollowResponse(false, "invalid input");
        failureUserFollowCountResponse = new UserFollowCountResponse("invalid input");

        Mockito.when(mockFollowDAO.getFollowStatus(invalidFollowStatusRequest)).thenReturn(failureFollowStatusResponse);
        Mockito.when(mockFollowDAO.setFollow(invalidFollowRequest)).thenReturn(failureFollowResponse);
        Mockito.when(mockFollowDAO.setUnfollow(invalidUnfollowRequest)).thenReturn(failureUnfollowResponse);
        Mockito.when(mockFollowDAO.getUserFollowCount(invalidUserFollowCountRequest)).thenReturn(failureUserFollowCountResponse);

        // Create a FollowService instance and wrap it with a spy that will use the mock service
        mFollowServiceImplSpy = Mockito.spy(new FollowServiceImpl());
        Mockito.when(mFollowServiceImplSpy.getFollowDAO()).thenReturn(mockFollowDAO);
    }

    @Test
    public void testGetFollowStatus_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        FollowStatusResponse response = mFollowServiceImplSpy.getFollowStatus(validFollowStatusRequest);
        Assertions.assertEquals(successFollowStatusResponse, response);
    }

    @Test
    public void testGetFollowStatus_invalidRequest_invalidResponse() throws IOException, TweeterRemoteException {
        FollowStatusResponse response = mFollowServiceImplSpy.getFollowStatus(invalidFollowStatusRequest);
        Assertions.assertEquals(failureFollowStatusResponse, response);
    }

    @Test
    public void testSetFollow_validFollowRequest_correctResponse() throws IOException, TweeterRemoteException {
        FollowResponse response = mFollowServiceImplSpy.setFollow(validFollowRequest);
        Assertions.assertEquals(successFollowResponse, response);
    }

    @Test
    public void testSetFollow_invalidFollowRequest_invalidResponse() throws IOException, TweeterRemoteException {
        FollowResponse response = mFollowServiceImplSpy.setFollow(invalidFollowRequest);
        Assertions.assertEquals(failureFollowResponse, response);
    }

    @Test
    public void testSetFollow_validUnfollowRequest_correctResponse() throws IOException, TweeterRemoteException {
        FollowResponse response = mFollowServiceImplSpy.setUnfollow(validUnfollowRequest);
        Assertions.assertEquals(successUnfollowResponse, response);
    }

    @Test
    public void testSetFollow_invalidUnfollowRequest_invalidResponse() throws IOException, TweeterRemoteException {
        FollowResponse response = mFollowServiceImplSpy.setUnfollow(invalidUnfollowRequest);
        Assertions.assertEquals(failureUnfollowResponse, response);
    }

    @Test
    public void testGetUserFollowCount_validUserFollowCountRequest_correctResponse() throws IOException, TweeterRemoteException {
       UserFollowCountResponse response = mFollowServiceImplSpy.getUserFollowCount(validUserFollowCountRequest);
       Assertions.assertEquals(successUserFollowCountResponse, response);
    }

    @Test
    public void testGetUserFollowCount_invalidUserFollowCountRequest_invalidResponse() throws IOException, TweeterRemoteException {
        UserFollowCountResponse response = mFollowServiceImplSpy.getUserFollowCount(invalidUserFollowCountRequest);
        Assertions.assertEquals(failureUserFollowCountResponse, response);
    }

}
