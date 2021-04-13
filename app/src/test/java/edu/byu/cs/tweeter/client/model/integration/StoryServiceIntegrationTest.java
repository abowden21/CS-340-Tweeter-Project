package edu.byu.cs.tweeter.client.model.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;

import edu.byu.cs.tweeter.client.model.service.StoryServiceProxy;
import edu.byu.cs.tweeter.shared.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.model.request.StoryRequest;
import edu.byu.cs.tweeter.shared.model.response.StoryResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StoryServiceIntegrationTest {
    private StoryRequest validRequest;
    private StoryServiceProxy storyService;

    @BeforeEach
    public void setup() {
        validRequest = new StoryRequest("@testUser", 10, LocalDateTime.now().toString());
        storyService = new StoryServiceProxy();
    }

    @Test
    public void testGetStory_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        StoryResponse response = storyService.getStory(validRequest);
        assertTrue(response.getStatuses().size() > 0);
        assertEquals("@testUser", response.getStatuses().get(0).getUser().getAlias());
    }
}
