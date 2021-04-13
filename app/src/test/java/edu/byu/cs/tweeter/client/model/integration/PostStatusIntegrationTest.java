package edu.byu.cs.tweeter.client.model.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.PostStatusServiceProxy;
import edu.byu.cs.tweeter.shared.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.model.request.PostStatusRequest;
import edu.byu.cs.tweeter.shared.model.response.PostStatusResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PostStatusIntegrationTest {

    PostStatusServiceProxy service;
    PostStatusRequest validRequest;

    @BeforeEach
    void setup() {
        // Dummy data, allows for everything right now.
        validRequest = new PostStatusRequest("integration_test_token", "<integration_test_status>");
        service = new PostStatusServiceProxy();
    }

    @Test
    void test_postStatusSuccess() throws IOException, TweeterRemoteException {
        PostStatusResponse response = service.sendStatus(validRequest);
        assertTrue(response.isSuccess());
        assertEquals(response.getStatus().getMessage(), validRequest.getMessage());
    }
}
