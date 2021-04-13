package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.byu.cs.tweeter.server.DataAccessException;
import edu.byu.cs.tweeter.server.dao.FeedDAO;
import edu.byu.cs.tweeter.shared.model.domain.AuthToken;
import edu.byu.cs.tweeter.shared.model.domain.Status;
import edu.byu.cs.tweeter.shared.model.domain.User;
import edu.byu.cs.tweeter.shared.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.model.request.FeedRequest;
import edu.byu.cs.tweeter.shared.model.response.FeedResponse;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.eq;

public class FeedServiceImplTest {
    private FeedRequest validRequest;
    private FeedRequest invalidRequest;
    private FeedServiceImpl feedServiceSpy;
    private FeedDAO feedDAO;

    private String validUser = "validUser";
    private String invalidUser = "invalidUser";

    List<Status> statuses;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException, DataAccessException {
        final User user1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        final User user2 = new User("FirstName2", "LastName2",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        final Status status1 = new Status(LocalDateTime.parse("2021-02-15T01:01:05"),
                "message1", user1);
        final Status status2 = new Status(LocalDateTime.parse("2021-02-15T01:01:06"),
                "message2", user2);
        statuses = new ArrayList<>();
        statuses.add(status1);
        statuses.add(status2);

        feedDAO = Mockito.mock(FeedDAO.class);

        validRequest = new FeedRequest(new AuthToken("token", validUser), 2, LocalDateTime.parse("2021-02-15T01:01:01"));
        invalidRequest = new FeedRequest(new AuthToken("token", invalidUser), 0, "");

        Mockito.when(feedDAO.getFeed(eq(validUser), Mockito.anyString(), Mockito.anyInt())).thenReturn(statuses);
        Mockito.doThrow(RuntimeException.class).when(feedDAO).getFeed(eq(invalidUser), Mockito.anyString(), Mockito.anyInt());

        feedServiceSpy = Mockito.spy(new FeedServiceImpl());
        Mockito.when(feedServiceSpy.getFeedDAO()).thenReturn(feedDAO);
    }

    @Test
    public void testGetFeed_validRequest_successResponse() throws DataAccessException {
        FeedResponse response = feedServiceSpy.getFeed(validRequest);
        Assertions.assertEquals(statuses, response.getStatuses());
    }

    @Test
    public void testGetFeed_invalidRequest_failureResponse() {
        assertThrows(RuntimeException.class, () ->  feedServiceSpy.getFeed(invalidRequest));
    }
}
