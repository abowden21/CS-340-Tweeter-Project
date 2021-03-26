package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;

import edu.byu.cs.tweeter.server.dao.FeedDAO;
import edu.byu.cs.tweeter.shared.model.domain.AuthToken;
import edu.byu.cs.tweeter.shared.model.domain.Status;
import edu.byu.cs.tweeter.shared.model.domain.User;
import edu.byu.cs.tweeter.shared.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.model.request.FeedRequest;
import edu.byu.cs.tweeter.shared.model.response.FeedResponse;

public class FeedServiceImplTest {
    private FeedRequest validRequest;
    private FeedRequest invalidRequest;

    private FeedResponse successResponse;
    private FeedResponse failureResponse;

    private FeedServiceImpl feedServiceSpy;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        final User user1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        final User user2 = new User("FirstName2", "LastName2",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        final Status status1 = new Status(LocalDateTime.parse("2021-02-15T01:01:05"),
                "message1", user1);
        final Status status2 = new Status(LocalDateTime.parse("2021-02-15T01:01:06"),
                "message2", user2);
        AuthToken validToken = new AuthToken("validToken", "FirstName1LastName1");


        validRequest = new FeedRequest(validToken, 2, LocalDateTime.parse("2021-02-15T01:01:01"));
        invalidRequest = new FeedRequest(null, 0, "");

        successResponse = new FeedResponse(Arrays.asList(status1, status2), false);
        FeedDAO feedDAO = Mockito.mock(FeedDAO.class);
        Mockito.when(feedDAO.getFeed(validRequest)).thenReturn(successResponse);

        failureResponse = new FeedResponse("An exception occurred");
        Mockito.when(feedDAO.getFeed(invalidRequest)).thenReturn(failureResponse);

        feedServiceSpy = Mockito.spy(new FeedServiceImpl());
        Mockito.when(feedServiceSpy.getFeedDAO()).thenReturn(feedDAO);
    }

    @Test
    public void testGetFeed_validRequest_successResponse() throws IOException, TweeterRemoteException {
        FeedResponse response = feedServiceSpy.getFeed(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testGetFeed_invalidRequest_failureResponse() throws IOException, TweeterRemoteException {
        FeedResponse response = feedServiceSpy.getFeed(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}
