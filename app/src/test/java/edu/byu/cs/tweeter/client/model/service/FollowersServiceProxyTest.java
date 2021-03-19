package edu.byu.cs.tweeter.client.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import edu.byu.cs.tweeter.shared.model.domain.User;
import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.shared.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.model.request.FollowersRequest;
import edu.byu.cs.tweeter.shared.model.response.FollowersResponse;

public class FollowersServiceProxyTest {

    private FollowersRequest validRequest;
    private FollowersRequest invalidRequest;

    private FollowersResponse successResponse;
    private FollowersResponse failureResponse;

    private FollowersServiceProxy followingServiceSpy;

    /**
     * Create a FollowersService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null);

        User resultUser1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User resultUser2 = new User("FirstName2", "LastName2",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
        User resultUser3 = new User("FirstName3", "LastName3",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");

        // Setup request objects to use in the tests
        validRequest = new FollowersRequest(currentUser.getAlias(), 3, null);
        invalidRequest = new FollowersRequest(null, 0, null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new FollowersResponse(Arrays.asList(resultUser1, resultUser2, resultUser3), false);
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.getFollowers(validRequest, "/follow/getfollowers")).thenReturn(successResponse);

        failureResponse = new FollowersResponse("An exception occurred");
        Mockito.when(mockServerFacade.getFollowers(invalidRequest, "/follow/getfollowers")).thenReturn(failureResponse);

        // Create a FollowersService instance and wrap it with a spy that will use the mock service
        followingServiceSpy = Mockito.spy(new FollowersServiceProxy());
        Mockito.when(followingServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    @Test
    public void testGetFollowers_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        FollowersResponse response = followingServiceSpy.getFollowers(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testGetFollowers_validRequest_loadsProfileImages() throws IOException, TweeterRemoteException {
        FollowersResponse response = followingServiceSpy.getFollowers(validRequest);

        for(User user : response.getFollowers()) {
            Assertions.assertNotNull(user.getImageBytes());
        }
    }

    @Test
    public void testGetFollowers_invalidRequest_returnsNoFollowers() throws IOException, TweeterRemoteException {
        FollowersResponse response = followingServiceSpy.getFollowers(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}
