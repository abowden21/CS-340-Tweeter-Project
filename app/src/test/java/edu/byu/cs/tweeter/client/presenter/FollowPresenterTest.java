package edu.byu.cs.tweeter.client.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.shared.model.domain.AuthToken;
import edu.byu.cs.tweeter.shared.model.domain.User;
import edu.byu.cs.tweeter.client.model.service.FollowServiceProxy;
import edu.byu.cs.tweeter.shared.model.request.FollowRequest;
import edu.byu.cs.tweeter.shared.model.request.FollowStatusRequest;
import edu.byu.cs.tweeter.shared.model.request.UserFollowCountRequest;
import edu.byu.cs.tweeter.shared.model.response.FollowResponse;
import edu.byu.cs.tweeter.shared.model.response.FollowStatusResponse;
import edu.byu.cs.tweeter.shared.model.response.UserFollowCountResponse;

public class FollowPresenterTest {

    private FollowRequest validFollowRequest;
    private FollowResponse successFollowResponse;

    private FollowRequest validUnfollowRequest;
    private FollowResponse successUnfollowResponse;

    private FollowStatusRequest validFollowStatusRequest;
    private FollowStatusResponse successFollowStatusResponse;

    private FollowServiceProxy mMockFollowServiceProxy;
    private FollowPresenter presenter;

    private UserFollowCountRequest validUserFollowCountRequest;
    private UserFollowCountResponse successUserFollowCountResponse;

    /**
     * Create a FollowingService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() throws IOException {
        User currentUser = new User("FirstName", "LastName", null);
        User otherUser =  new User("FirstName2", "LastName2", null);
        AuthToken authToken = new AuthToken("mockToken");

        // Setup request objects to use in the tests
        validFollowStatusRequest = new FollowStatusRequest(currentUser.getAlias(), otherUser.getAlias());
        validFollowRequest = new FollowRequest(authToken, true, currentUser.getAlias(), otherUser.getAlias());
        validUnfollowRequest = new FollowRequest(authToken, false, currentUser.getAlias(), otherUser.getAlias());
        validUserFollowCountRequest = new UserFollowCountRequest("alias");

        // Setup a mock ServerFacade that will return known responses
        successFollowStatusResponse = new FollowStatusResponse(true, true);
        successFollowResponse = new FollowResponse(true, true);
        successUnfollowResponse = new FollowResponse(false, true);
        successUserFollowCountResponse = new UserFollowCountResponse(100, 20);



        // Create a FollowingService instance and wrap it with a spy that will use the mock service
        mMockFollowServiceProxy = Mockito.mock(FollowServiceProxy.class);
        Mockito.when(mMockFollowServiceProxy.getFollowStatus(validFollowStatusRequest)).thenReturn(successFollowStatusResponse);
        Mockito.when(mMockFollowServiceProxy.setFollow(validFollowRequest)).thenReturn(successFollowResponse);
        Mockito.when(mMockFollowServiceProxy.setFollow(validUnfollowRequest)).thenReturn(successUnfollowResponse);
        Mockito.when(mMockFollowServiceProxy.getUserFollowCount(validUserFollowCountRequest)).thenReturn(successUserFollowCountResponse);


        // Wrap a FollowingPresenter in a spy that will use the mock service.
        presenter = Mockito.spy(new FollowPresenter());
        Mockito.when(presenter.getFollowService()).thenReturn(mMockFollowServiceProxy);
    }

    @Test
    public void testGetFollowStatus_returnsServiceResult() throws IOException {

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(successFollowStatusResponse, presenter.getFollowStatus(validFollowStatusRequest));
    }

    @Test
    public void testGetFollowStatus_serviceThrowsIOException_presenterThrowsIOException() throws IOException {
        Mockito.when(mMockFollowServiceProxy.getFollowStatus(validFollowStatusRequest)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> {
            presenter.getFollowStatus(validFollowStatusRequest);
        });
    }

    @Test
    public void testSetFollow_returnsServiceResult() throws IOException {

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(successFollowResponse, presenter.setFollow(validFollowRequest));
    }

    @Test
    public void testSetFollow_serviceThrowsIOException_presenterThrowsIOException() throws IOException {
        Mockito.when(mMockFollowServiceProxy.setFollow(validFollowRequest)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> {
            presenter.setFollow(validFollowRequest);
        });
    }

    @Test
    public void testSetUnfollow_returnsServiceResult() throws IOException {

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(successFollowResponse, presenter.setFollow(validFollowRequest));
    }

    @Test
    public void testSetUnfollow_serviceThrowsIOException_presenterThrowsIOException() throws IOException {
        Mockito.when(mMockFollowServiceProxy.setFollow(validUnfollowRequest)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> {
            presenter.setFollow(validUnfollowRequest);
        });
    }
}
