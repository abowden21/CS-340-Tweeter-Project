package edu.byu.cs.tweeter.client.model.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import edu.byu.cs.tweeter.client.model.service.FeedServiceProxy;
import edu.byu.cs.tweeter.shared.model.domain.AuthToken;
import edu.byu.cs.tweeter.shared.model.domain.Status;
import edu.byu.cs.tweeter.shared.model.domain.User;
import edu.byu.cs.tweeter.shared.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.model.request.FeedRequest;
import edu.byu.cs.tweeter.shared.model.response.FeedResponse;

public class FeedServiceIntegrationTest {
    private FeedRequest validRequest;

    private FeedResponse successResponse;

    private List<Status> statuses;

    private FeedServiceProxy feedService;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {

        AuthToken validAuthToken = new AuthToken("068fd201-03ec-4965-adda-87ad6c0b011a", "@bluedrift");
        String nullString = null;
        validRequest = new FeedRequest(validAuthToken, 10, nullString);
        successResponse = new FeedResponse(statuses, true);

        feedService = new FeedServiceProxy();
    }

    @Test
    public void testFeed_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        FeedResponse response = feedService.getFeed(validRequest);

        Assertions.assertEquals(10, response.getStatuses().size());
    }

    @Test
    public void testGetFeed_validRequest_loadsProfileImages() throws IOException, TweeterRemoteException {
        FeedResponse response = feedService.getFeed(validRequest);

        for (Status status: response.getStatuses()) {
            Assertions.assertNotNull(status.getUser().getImageBytes());
        }
    }
}
