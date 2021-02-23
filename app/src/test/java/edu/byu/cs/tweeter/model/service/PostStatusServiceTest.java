package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.time.LocalDateTime;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.service.response.PostStatusResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PostStatusServiceTest {

    PostStatusRequest postStatusRequestValid;
    PostStatusRequest postStatusRequestInvalid_authToken;
    PostStatusRequest postStatusRequestInvalid_contentLength;
    PostStatusResponse postStatusResponseValid;
    PostStatusResponse postStatusResponseInvalid_authToken;
    PostStatusResponse postStatusResponseInvalid_contentLength;

    ServerFacade serverFacadeMock;
    PostStatusService postStatusServiceSpy;

    @BeforeEach
    public void setup() {
        AuthToken validAuthToken = new AuthToken("mockToken");
        AuthToken invalidAuthToken = new AuthToken("invalidToken!");
        String validPost = "Post";
        String invalidPost = new String(new char[141]).replace("\0", "X"); // 141 characters of X - too long.
        User validUser = new User("mockFirst", "mockLast", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        Status validStatus = new Status(LocalDateTime.parse("2021-01-01T00:00:00"), validPost, validUser);

        postStatusRequestValid = new PostStatusRequest(validAuthToken, validPost);
        postStatusRequestInvalid_authToken = new PostStatusRequest(invalidAuthToken, validPost);
        postStatusRequestInvalid_contentLength = new PostStatusRequest(validAuthToken, invalidPost);
        postStatusResponseValid = new PostStatusResponse(validStatus);
        postStatusResponseInvalid_authToken = new PostStatusResponse("Auth token invalid.");
        postStatusResponseInvalid_contentLength = new PostStatusResponse("Post is too long.");

        serverFacadeMock = Mockito.mock(ServerFacade.class);

        Mockito.when(serverFacadeMock.sendStatus(postStatusRequestValid)).thenReturn(postStatusResponseValid);
        Mockito.when(serverFacadeMock.sendStatus(postStatusRequestInvalid_authToken)).thenReturn(postStatusResponseInvalid_authToken);
        Mockito.when(serverFacadeMock.sendStatus(postStatusRequestInvalid_contentLength)).thenReturn(postStatusResponseInvalid_contentLength);

        postStatusServiceSpy = Mockito.spy(new PostStatusService());
        Mockito.when(postStatusServiceSpy.getServerFacade()).thenReturn(serverFacadeMock);
    }

    @Test
    public void testPostStatus_validResponse() throws IOException {
        PostStatusResponse response = postStatusServiceSpy.sendStatus(postStatusRequestValid);
        assertEquals(postStatusResponseValid, response);
    }

    @Test
    public void testPostStatus_invalidResponse_badAuthToken() throws IOException {
        PostStatusResponse response = postStatusServiceSpy.sendStatus(postStatusRequestInvalid_authToken);
        assertEquals(postStatusResponseInvalid_authToken, response);
    }

    @Test
    public void testPostStatus_invalidResponse_badContentLength() throws IOException {
        PostStatusResponse response = postStatusServiceSpy.sendStatus(postStatusRequestInvalid_contentLength);
        assertEquals(postStatusResponseInvalid_contentLength, response);
    }
}
