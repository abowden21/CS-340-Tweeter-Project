package edu.byu.cs.tweeter.client.model.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.client.model.service.FollowServiceProxy;
import edu.byu.cs.tweeter.client.model.service.FollowersServiceProxy;
import edu.byu.cs.tweeter.shared.model.domain.AuthToken;
import edu.byu.cs.tweeter.shared.model.domain.User;
import edu.byu.cs.tweeter.shared.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.model.request.FollowRequest;
import edu.byu.cs.tweeter.shared.model.request.FollowersRequest;
import edu.byu.cs.tweeter.shared.model.response.FollowersResponse;

public class FollowersServiceIntegrationTest {
    private FollowersRequest validRequest;

    private FollowersResponse successResponse;

    private FollowersServiceProxy followingService;

    private User currentUser = new User("FirstName", "LastName", null);
    private User otherUser =  new User("FirstName2", "LastName2", null);
    private AuthToken authToken = new AuthToken("<mockToken>", "test");
    FollowServiceProxy followServiceProxy = new FollowServiceProxy();

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

        followServiceProxy.setFollow(new FollowRequest(authToken, true, "guy2", "guy1"));
        followServiceProxy.setFollow(new FollowRequest(authToken, true, "guy3", "guy1"));
        followServiceProxy.setFollow(new FollowRequest(authToken, true, "guy4", "guy1"));

        validRequest = new FollowersRequest("guy1", 2, null);
        successResponse = new FollowersResponse(Arrays.asList(resultUser2, resultUser1), true);

        followingService = new FollowersServiceProxy();
    }

    @AfterEach
    public void cleanup() throws IOException, TweeterRemoteException {
        followServiceProxy.setFollow(new FollowRequest(authToken, false, "guy2", "guy1"));
        followServiceProxy.setFollow(new FollowRequest(authToken, false, "guy3", "guy1"));
        followServiceProxy.setFollow(new FollowRequest(authToken, false, "guy4", "guy1"));
    }

    @Test
    public void testGetFollowers_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        FollowersResponse response = followingService.getFollowers(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testGetFollowers_validRequest_loadsProfileImages() throws IOException, TweeterRemoteException {
        FollowersResponse response = followingService.getFollowers(validRequest);

        for(User user : response.getFollowers()) {
            Assertions.assertNotNull(user.getImageBytes());
        }
    }
}
