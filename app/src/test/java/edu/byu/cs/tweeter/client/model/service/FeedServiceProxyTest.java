package edu.byu.cs.tweeter.client.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;

import edu.byu.cs.tweeter.shared.model.domain.AuthToken;
import edu.byu.cs.tweeter.shared.model.domain.Status;
import edu.byu.cs.tweeter.shared.model.domain.User;
import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.shared.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.model.request.FeedRequest;
import edu.byu.cs.tweeter.shared.model.response.FeedResponse;

public class FeedServiceProxyTest {

    private FeedRequest validRequest;
    private FeedRequest invalidRequest;

    private FeedResponse successResponse;
    private FeedResponse failureResponse;

    private FeedServiceProxy feedServiceProxySpy;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User user1 = new User("firstname1", "lastname1", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User user2 = new User("firstname2", "lastname2", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");

        Status resultStatus1 = new Status(LocalDateTime.parse("2021-02-13T01:01:01"), "message1", user1);
        Status resultStatus2 = new Status(LocalDateTime.parse("2021-02-13T01:01:02"), "message2", user1);
        Status resultStatus3 = new Status(LocalDateTime.parse("2021-02-13T01:01:03"), "message3", user2);
        Status resultStatus4 = new Status(LocalDateTime.parse("2021-02-13T01:01:04"), "message4", user2);
        AuthToken validToken = new AuthToken("validToken", "test");
        AuthToken invalidToken = new AuthToken("invalidToken", "test");

        validRequest = new FeedRequest(validToken, 4, "");
        invalidRequest = new FeedRequest(invalidToken, 0, "null");

        successResponse = new FeedResponse(Arrays.asList(resultStatus1, resultStatus2, resultStatus3, resultStatus4), false);
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.getFeed(validRequest, "/view/feed")).thenReturn(successResponse);

        failureResponse = new FeedResponse("An exception occurred");
        Mockito.when(mockServerFacade.getFeed(invalidRequest, "/view/feed")).thenReturn(failureResponse);

        feedServiceProxySpy = Mockito.spy(new FeedServiceProxy());
        Mockito.when(feedServiceProxySpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    @Test
    public void testGetFeed_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        FeedResponse response = feedServiceProxySpy.getFeed(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testGetFeed_validRequest_loadsUsers() throws IOException, TweeterRemoteException {
        FeedResponse response = feedServiceProxySpy.getFeed(validRequest);

        for (Status status: response.getStatuses()) {
            Assertions.assertNotNull(status.getUser());
        }
    }

    @Test
    public void testGetFeed_invalidRequest_returnsNoStatuses() throws IOException, TweeterRemoteException {
        FeedResponse response = feedServiceProxySpy.getFeed(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}
