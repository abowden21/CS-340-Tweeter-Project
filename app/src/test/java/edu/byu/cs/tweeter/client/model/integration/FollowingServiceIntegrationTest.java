package edu.byu.cs.tweeter.client.model.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.util.Arrays;


import edu.byu.cs.tweeter.client.model.service.FollowServiceProxy;
import edu.byu.cs.tweeter.client.model.service.FollowingServiceProxy;
import edu.byu.cs.tweeter.shared.model.domain.AuthToken;
import edu.byu.cs.tweeter.shared.model.domain.User;
import edu.byu.cs.tweeter.shared.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.model.request.FollowRequest;
import edu.byu.cs.tweeter.shared.model.request.FollowingRequest;
import edu.byu.cs.tweeter.shared.model.response.FollowersResponse;
import edu.byu.cs.tweeter.shared.model.response.FollowingResponse;

public class FollowingServiceIntegrationTest {
    private FollowingRequest validRequest;

    private FollowingResponse successResponse;

    private FollowingServiceProxy followingService;
    private FollowServiceProxy followServiceProxy = new FollowServiceProxy();
    private AuthToken authToken = new AuthToken("<mockToken>", "test");

    /**
     * Create a FollowersService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {

        final String URL = "https://upload.wikimedia.org/wikipedia/en/0/00/The_Child_aka_Baby_Yoda_%28Star_Wars%29.jpg";

        User resultUser1 = new User("Mr.", "Guy 2", "guy2", URL);
        User resultUser2 = new User("Mr.", "Guy 3", "guy3", URL);
        User resultUser3 = new User("Mr.",  "Guy 4", "guy4", URL);

        followServiceProxy.setFollow(new FollowRequest(authToken, true, "guy1", "guy2"));
        followServiceProxy.setFollow(new FollowRequest(authToken, true, "guy1", "guy3"));
        followServiceProxy.setFollow(new FollowRequest(authToken, true, "guy1", "guy4"));


        User currentUser = new User("FirstName", "LastName", null);

        validRequest = new FollowingRequest("guy1", 2, null);
        successResponse = new FollowingResponse(Arrays.asList(resultUser1, resultUser2), true);

        followingService = new FollowingServiceProxy();
    }

    @AfterEach
    public void cleanup() throws IOException, TweeterRemoteException {
        followServiceProxy.setFollow(new FollowRequest(authToken, false, "guy1", "guy2"));
        followServiceProxy.setFollow(new FollowRequest(authToken, false, "guy1", "guy3"));
        followServiceProxy.setFollow(new FollowRequest(authToken, false, "guy1", "guy4"));
    }

    @Test
    public void testGetFollowers_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        FollowingResponse response = followingService.getFollowees(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testGetFollowers_validRequest_loadsProfileImages() throws IOException, TweeterRemoteException {
        FollowingResponse response = followingService.getFollowees(validRequest);

        for(User user : response.getFollowees()) {
            Assertions.assertNotNull(user.getImageBytes());
        }
    }
}
