package edu.byu.cs.tweeter.client.model.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.util.Arrays;


import edu.byu.cs.tweeter.client.model.service.FollowingServiceProxy;
import edu.byu.cs.tweeter.shared.model.domain.User;
import edu.byu.cs.tweeter.shared.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.model.request.FollowingRequest;
import edu.byu.cs.tweeter.shared.model.response.FollowingResponse;

public class FollowingServiceIntegrationTest {
    private FollowingRequest validRequest;

    private FollowingResponse successResponse;

    private FollowingServiceProxy followingService;

    /**
     * Create a FollowersService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {

        final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
        final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";

        User currentUser = new User("FirstName", "LastName", null);

        User resultUser1 = new User("Allen", "Anderson", MALE_IMAGE_URL);
        User resultUser2 = new User("Amy", "Ames", FEMALE_IMAGE_URL);
        User resultUser3 = new User("Bob", "Bobson", MALE_IMAGE_URL);

        validRequest = new FollowingRequest(currentUser.getAlias(), 3, null);
        successResponse = new FollowingResponse(Arrays.asList(resultUser1, resultUser2, resultUser3), true);

        followingService = new FollowingServiceProxy();
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
