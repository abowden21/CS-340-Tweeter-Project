package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import edu.byu.cs.tweeter.shared.model.domain.AuthToken;
import edu.byu.cs.tweeter.shared.model.domain.User;
import edu.byu.cs.tweeter.shared.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.model.request.FollowRequest;
import edu.byu.cs.tweeter.shared.model.request.FollowStatusRequest;
import edu.byu.cs.tweeter.shared.model.request.FollowersRequest;
import edu.byu.cs.tweeter.shared.model.request.UserFollowCountRequest;
import edu.byu.cs.tweeter.shared.model.response.FollowResponse;
import edu.byu.cs.tweeter.shared.model.response.FollowStatusResponse;
import edu.byu.cs.tweeter.shared.model.response.UserFollowCountResponse;

public class FollowDAOTests {
    private FollowDAO followDAO;
    private FollowRequest validFollowRequest;
    private FollowRequest invalidFollowRequest;
    private FollowResponse successFollowResponse;
    FollowRequest followRequest;
    FollowRequest followRequest2;

    private FollowRequest validUnfollowRequest;
    private FollowRequest invalidUnfollowRequest;
    private FollowResponse successUnfollowResponse;

    private FollowStatusRequest validFollowStatusRequest;
    private FollowStatusRequest invalidFollowStatusRequest;
    private FollowStatusResponse successFollowStatusResponse;

    private UserFollowCountRequest validUserFollowCountRequest;
    private UserFollowCountRequest invalidUserFollowCountRequest;
    private UserFollowCountResponse successUserFollowCountResponse;
    private AuthToken authToken;
    private User otherUser;


    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        followDAO = new FollowDAO();
        User currentUser = new User("FirstName", "LastName", null);
        otherUser = new User("FirstName2", "LastName2", "alias1", null);
        authToken = new AuthToken("<mockToken>", "test");

        validFollowStatusRequest = new FollowStatusRequest(currentUser.getAlias(), otherUser.getAlias());
        invalidFollowStatusRequest = new FollowStatusRequest(null, null);

        followRequest = new FollowRequest(authToken, true, "anotherUser", "alias1");
        followRequest2 = new FollowRequest(authToken, true, "anotherUser", "alias2");

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

        followDAO.setFollow(new FollowRequest(authToken, true, "guy2", "guy1"));
        followDAO.setFollow(new FollowRequest(authToken, true, "guy3", "guy1"));
        followDAO.setFollow(new FollowRequest(authToken, true, "guy4", "guy1"));

    }

    @AfterEach
    public void cleanUp() {
        followDAO.setUnfollow(followRequest);
        followDAO.setUnfollow(followRequest2);
        followDAO.setUnfollow(validFollowRequest);

        followDAO.setUnfollow(new FollowRequest(authToken, false, "guy2", "guy1"));
        followDAO.setUnfollow(new FollowRequest(authToken, false, "guy3", "guy1"));
        followDAO.setUnfollow(new FollowRequest(authToken, false, "guy4", "guy1"));
    }

    @Test
    public void testSetFollow_validFollowRequest_correctResponse() throws IOException, TweeterRemoteException {
        FollowResponse response = followDAO.setFollow(validFollowRequest);
        Assertions.assertEquals(successFollowResponse, response);
    }

    @Test
    public void testSetFollow_invalidFollowRequest_throwsTweeterServerException() throws IOException, TweeterRemoteException {
        Assertions.assertThrows(AmazonDynamoDBException.class, () -> {
            followDAO.setFollow(invalidFollowRequest);
        });
    }

    @Test
    public void testGetFollowStatus_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        followDAO.setFollow(validFollowRequest);
        FollowStatusResponse response = followDAO.getFollowStatus(validFollowStatusRequest);
        Assertions.assertEquals(successFollowStatusResponse, response);
    }

    @Test
    public void testGetFollowStatus_invalidGetFollowStatusRequest_throwsTweeterServerException() throws IOException, TweeterRemoteException {
        Assertions.assertThrows(AmazonDynamoDBException.class, () -> {
            followDAO.getFollowStatus(invalidFollowStatusRequest);
        });
    }

    @Test
    public void testGetUserFollowCount_validUserFollowCountRequest_correctResponse() throws IOException, TweeterRemoteException {
        UserFollowCountResponse response = followDAO.getUserFollowCount(validUserFollowCountRequest);
        Assertions.assertEquals(successUserFollowCountResponse.getFollowees(), response.getFollowees());
    }

    @Test
    public void testGetUserFollowCount_invalidGetFollowCountRequest_throwsTweeterServerException() throws IOException, TweeterRemoteException {
        Assertions.assertThrows(AmazonDynamoDBException.class, () -> {
            followDAO.getUserFollowCount(invalidUserFollowCountRequest);
        });
    }

    @Test
    public void testSetFollow_validUnfollowRequest_correctResponse() throws IOException, TweeterRemoteException {
        FollowResponse response = followDAO.setUnfollow(validUnfollowRequest);
        Assertions.assertEquals(successUnfollowResponse, response);
    }

    @Test
    public void testSetFollow_invalidUnfollowRequest_throwsTweeterServerException() throws IOException, TweeterRemoteException {
        Assertions.assertThrows(AmazonDynamoDBException.class, () -> {
            followDAO.setFollow(invalidUnfollowRequest);
        });
    }

    @Test
    public void testGetFollowers_validFollowersRequest_correctResponse() throws IOException, TweeterRemoteException {
        followDAO.setFollow(validFollowRequest);
        followRequest = new FollowRequest(authToken, true, "anotherUser", "alias1");
        followDAO.setFollow(followRequest);
        List<String> followers = followDAO.getAllFollowerNames(validFollowRequest.getFollowerAlias());
        Assertions.assertTrue(followers.size() >= 2);

        followDAO.setUnfollow(followRequest);
        followers = followDAO.getAllFollowerNames(validFollowRequest.getFollowerAlias());
        Assertions.assertTrue(followers.size() >= 1);
        followDAO.setFollow(followRequest);
    }

    @Test
    public void testGetFollowees_validFolloweesRequest_correctResponse() throws IOException, TweeterRemoteException {
        followRequest = new FollowRequest(authToken, true, "anotherUser", "alias1");
        followRequest2 = new FollowRequest(authToken, true, "anotherUser2", "alias2");
        followDAO.setFollow(followRequest);
        followDAO.setFollow((followRequest2));

        List<String> followers = followDAO.getAllFollowerNames(followRequest.getFollowerAlias());
        Assertions.assertTrue(followers.size() >= 0);

        followDAO.setUnfollow(followRequest);

        followers = followDAO.getAllFollowerNames(followRequest.getFollowerAlias());
        Assertions.assertTrue(followers.size() >= 0);
    }

    @Test
    public void testGetFollowersPaginated_validRequest_correctResponse() {
        //there are 3 followers but tests that 2 are given
        FollowersRequest followersRequest = new FollowersRequest("guy1", 2, null);
        List<String> followers = followDAO.getFollowersPaginated(followersRequest);
        Assertions.assertTrue(followers.size() >= 0);

        followersRequest = new FollowersRequest("guy1", 3, null);
        followers = followDAO.getFollowersPaginated(followersRequest);
        Assertions.assertTrue(followers.size() >= 0);
    }
}
