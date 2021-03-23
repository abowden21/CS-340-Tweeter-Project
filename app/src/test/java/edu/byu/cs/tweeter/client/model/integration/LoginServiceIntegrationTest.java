package edu.byu.cs.tweeter.client.model.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.LoginServiceProxy;
import edu.byu.cs.tweeter.shared.model.domain.AuthToken;
import edu.byu.cs.tweeter.shared.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.model.request.LoginRequest;
import edu.byu.cs.tweeter.shared.model.request.LogoutRequest;
import edu.byu.cs.tweeter.shared.model.request.RegisterRequest;
import edu.byu.cs.tweeter.shared.model.response.LoginResponse;
import edu.byu.cs.tweeter.shared.model.response.LogoutResponse;
import edu.byu.cs.tweeter.shared.model.response.RegisterResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginServiceIntegrationTest {

    LoginServiceProxy service;
    LoginRequest loginRequestValid;
    RegisterRequest registerRequestValid;
    LogoutRequest logoutRequestValid;

    @BeforeEach
    void setup() {
        service = new LoginServiceProxy();
        // Dummy credentials right now since that's what the server is set up to handle.
        loginRequestValid = new LoginRequest("testUser", "password");
        registerRequestValid = new RegisterRequest("first", "last", "user", "pass", new byte[0]);
        logoutRequestValid = new LogoutRequest(new AuthToken("dummy token", "alias"));
    }

    @Test
    void test_loginSuccess() throws IOException, TweeterRemoteException {
        LoginResponse response = service.login(loginRequestValid);
        assertTrue(response.isSuccess());
        assertEquals(response.getUser().getAlias(), "@TestUser"); // That's what the dummy service is returning.
    }

    @Test
    void test_registerSuccess() throws IOException, TweeterRemoteException {
        RegisterResponse response = service.register(registerRequestValid);
        assertTrue(response.isSuccess());
        assertEquals(response.getUser().getAlias(), registerRequestValid.getUsername());
    }

    @Test
    void test_logoutSuccess() throws IOException, TweeterRemoteException {
        LogoutResponse response = service.logout(logoutRequestValid);
        assertTrue(response.isSuccess());
    }
}
