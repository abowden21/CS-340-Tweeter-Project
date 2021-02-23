package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginServiceTest {

    LoginRequest loginRequestValid;
    LoginRequest loginRequestInvalid;
    LoginResponse loginResponseValid;
    LoginResponse loginResponseInvalid;

    RegisterRequest registerRequestValid;
    RegisterRequest registerRequestInvalid;
    RegisterResponse registerResponseValid;
    RegisterResponse registerResponseInvalid;

    LogoutRequest logoutRequestValid;
    LogoutRequest logoutRequestInvalid;
    LogoutResponse logoutResponseValid;
    LogoutResponse logoutResponseInvalid;

    LoginService loginServiceSpy;
    ServerFacade serverFacadeMock;

    @BeforeEach
    public void setup() {
        // Setup request objects to use in the tests
        loginRequestValid = new LoginRequest("mockUser", "mockPw");
        loginRequestInvalid = new LoginRequest("mockUser", "wrongPassword!!");
        registerRequestValid = new RegisterRequest("newUser", "mockPw", "First", "Last", new byte[1024]);
        registerRequestInvalid = new RegisterRequest("userNameAlreadyTaken", "mockPw", "First", "Last", new byte[1024]);
        AuthToken mockToken = new AuthToken("abcdToken");
        AuthToken nonexistentToken = new AuthToken("<This token does not exist!>");
        logoutRequestValid = new LogoutRequest(mockToken);
        logoutRequestInvalid = new LogoutRequest(nonexistentToken);

        // Setup a mock ServerFacade that will return known responses
        User mockUser = new User("First", "Last", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        AuthToken mockUserToken = new AuthToken("<mockToken>");
        loginResponseValid = new LoginResponse(mockUser, mockUserToken);
        loginResponseInvalid = new LoginResponse("Username or password does not match.");
        User newUser = new User("First", "Last", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        AuthToken newUserToken = new AuthToken("<mockToken>");
        registerResponseValid = new RegisterResponse(newUser, newUserToken);
        registerResponseInvalid = new RegisterResponse("Username is already taken.");
        logoutResponseValid = new LogoutResponse(true);
        logoutResponseInvalid = new LogoutResponse("Auth token invalid.");

        serverFacadeMock = Mockito.mock(ServerFacade.class);
        Mockito.when(serverFacadeMock.login(loginRequestValid)).thenReturn(loginResponseValid);
        Mockito.when(serverFacadeMock.login(loginRequestInvalid)).thenReturn(loginResponseInvalid);
        Mockito.when(serverFacadeMock.register(registerRequestValid)).thenReturn(registerResponseValid);
        Mockito.when(serverFacadeMock.register(registerRequestInvalid)).thenReturn(registerResponseInvalid);
        Mockito.when(serverFacadeMock.logout(logoutRequestValid)).thenReturn(logoutResponseValid);
        Mockito.when(serverFacadeMock.logout(logoutRequestInvalid)).thenReturn(logoutResponseInvalid);

        // Create a FollowingService instance and wrap it with a spy that will use the mock service
        loginServiceSpy = Mockito.spy(new LoginService());
        Mockito.when(loginServiceSpy.getServerFacade()).thenReturn(serverFacadeMock);
    }

    @Test
    public void testLogin_validResponse() throws IOException {
        LoginResponse response = loginServiceSpy.login(loginRequestValid);
        assertEquals(loginResponseValid, response);
    }

    @Test
    public void testLogin_invalidResponse() throws IOException {
        LoginResponse response = loginServiceSpy.login(loginRequestInvalid);
        assertEquals(loginResponseInvalid, response);
    }

    @Test
    public void testRegister_validResponse() throws IOException {
        LoginResponse response = loginServiceSpy.register(registerRequestValid);
        assertEquals(registerResponseValid, response);
    }

    @Test
    public void testRegister_invalidResponse() throws IOException {
        LoginResponse response = loginServiceSpy.register(registerRequestInvalid);
        assertEquals(registerResponseInvalid, response);
    }

    @Test
    public void testLogout_validResponse() throws IOException {
        LogoutResponse response = loginServiceSpy.logout(logoutRequestValid);
        assertEquals(logoutResponseValid, response);
    }

    @Test
    public void testLogout_invalidResponse() throws IOException {
        LogoutResponse response = loginServiceSpy.logout(logoutRequestInvalid);
        assertEquals(logoutResponseInvalid, response);
    }
}
