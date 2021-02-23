package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;

public class FeedServiceTest {

    private FeedRequest validRequest;
    private FeedRequest invalidRequest;

    private FeedResponse successResponse;
    private FeedResponse failureResponse;

    private FeedService feedServiceSpy;

    @BeforeEach
    public void setup() {
        User user1 = new User("firstname1", "lastname1", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User user2 = new User("firstname2", "lastname2", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");

        Status resultStatus1 = new Status(LocalDateTime.parse("2021-02-13T01:01:01"), "message1", user1);
        Status resultStatus2 = new Status(LocalDateTime.parse("2021-02-13T01:01:02"), "message2", user1);
        Status resultStatus3 = new Status(LocalDateTime.parse("2021-02-13T01:01:03"), "message3", user2);
        Status resultStatus4 = new Status(LocalDateTime.parse("2021-02-13T01:01:04"), "message4", user2);
        AuthToken validToken = new AuthToken("validToken");
        AuthToken invalidToken = new AuthToken("invalidToken");

        validRequest = new FeedRequest(validToken, 4, null);
        invalidRequest = new FeedRequest(invalidToken, 0, null);

        successResponse = new FeedResponse(Arrays.asList(resultStatus1, resultStatus2, resultStatus3, resultStatus4), false);
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.getFeed(validRequest)).thenReturn(successResponse);

        failureResponse = new FeedResponse("An exception occurred");
        Mockito.when(mockServerFacade.getFeed(invalidRequest)).thenReturn(failureResponse);

        feedServiceSpy = Mockito.spy(new FeedService());
        Mockito.when(feedServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    @Test
    public void testGetFeed_validRequest_correctResponse() throws IOException {
        FeedResponse response = feedServiceSpy.getFeed(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testGetFeed_validRequest_loadsUsers() throws IOException {
        FeedResponse response = feedServiceSpy.getFeed(validRequest);

        for (Status status: response.getStatuses()) {
            Assertions.assertNotNull(status.getUser());
        }
    }

    @Test
    public void testGetFeed_invalidRequest_returnsNoStatuses() throws IOException {
        FeedResponse response = feedServiceSpy.getFeed(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}
