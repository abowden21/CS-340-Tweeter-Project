package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import edu.byu.cs.tweeter.server.dao.FollowingDAO;
import edu.byu.cs.tweeter.server.service.FollowingServiceImpl;
import edu.byu.cs.tweeter.shared.model.domain.User;
import edu.byu.cs.tweeter.shared.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.model.request.FollowingRequest;
import edu.byu.cs.tweeter.shared.model.response.FollowingResponse;

public class FollowingServiceImplTest {

    private FollowingRequest validRequest;
    private FollowingRequest invalidRequest;

    private FollowingResponse successResponse;
    private FollowingResponse failureResponse;

    private FollowingServiceImpl followingServiceSpy;

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
        validRequest = new FollowingRequest(currentUser.getAlias(), 3, null);
        invalidRequest = new FollowingRequest(null, 0, null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new FollowingResponse(Arrays.asList(resultUser1, resultUser2, resultUser3), false);
        FollowingDAO followingDAO = Mockito.mock(FollowingDAO.class);
        Mockito.when(followingDAO.getFollowees(validRequest)).thenReturn(successResponse);

        failureResponse = new FollowingResponse("An exception occurred");
        Mockito.when(followingDAO.getFollowees(invalidRequest)).thenReturn(failureResponse);

        // Create a FollowersService instance and wrap it with a spy that will use the mock service
        followingServiceSpy = Mockito.spy(new FollowingServiceImpl());
        Mockito.when(followingServiceSpy.getFollowingDAO()).thenReturn(followingDAO);
    }

    @Test
    public void testGetFollowees_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        FollowingResponse response = followingServiceSpy.getFollowees(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testGetFollowees_invalidRequest_returnsNoFollowers() throws IOException, TweeterRemoteException {
        FollowingResponse response = followingServiceSpy.getFollowees(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}
