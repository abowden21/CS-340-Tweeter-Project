package edu.byu.cs.tweeter.client.model.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.GetUserServiceProxy;
import edu.byu.cs.tweeter.shared.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.model.request.GetUserRequest;
import edu.byu.cs.tweeter.shared.model.response.GetUserResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetUserServiceIntegrationTest {

    GetUserRequest getUserRequestValid;
    GetUserServiceProxy service;

    String testAlias = "@testUser";

    @BeforeEach
    void setup() {
        getUserRequestValid = new GetUserRequest(testAlias);
        service = new GetUserServiceProxy();
    }

    @Test
    void test_getUserSuccess() throws IOException, TweeterRemoteException {
        GetUserResponse response = service.getUser(getUserRequestValid);
        assertTrue(response.isSuccess());
        assertEquals(response.getRetrievedUser().getAlias(), testAlias);
        assertNotNull(response.getRetrievedUser().getImageUrl());
        assertEquals("first", response.getRetrievedUser().getFirstName());
        assertEquals("last", response.getRetrievedUser().getLastName());
    }
}
