package edu.byu.cs.tweeter.client.model.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.GetUserServiceProxy;
import edu.byu.cs.tweeter.shared.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.model.request.GetUserRequest;
import edu.byu.cs.tweeter.shared.model.response.GetUserResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetUserServiceIntegrationTest {

    GetUserRequest getUserRequestValid;
    GetUserServiceProxy service;

    @BeforeEach
    void setup() {
        getUserRequestValid = new GetUserRequest("userAlias");
        service = new GetUserServiceProxy();
    }

    @Test
    void test_getUserSuccess() throws IOException, TweeterRemoteException {
        GetUserResponse response = service.getUser(getUserRequestValid);
        assertTrue(response.isSuccess());
        assertEquals(response.getRetrievedUser().getAlias(), "@TestUser"); // That's what the lambda is currently returning (dummy data)
    }
}
