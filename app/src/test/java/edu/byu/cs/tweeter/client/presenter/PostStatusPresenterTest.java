package edu.byu.cs.tweeter.client.presenter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.time.LocalDateTime;

import edu.byu.cs.tweeter.shared.model.domain.AuthToken;
import edu.byu.cs.tweeter.shared.model.domain.Status;
import edu.byu.cs.tweeter.shared.model.domain.User;
import edu.byu.cs.tweeter.client.model.service.PostStatusServiceProxy;
import edu.byu.cs.tweeter.shared.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.model.request.PostStatusRequest;
import edu.byu.cs.tweeter.shared.model.response.PostStatusResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PostStatusPresenterTest {

    PostStatusRequest postStatusRequest;
    PostStatusResponse postStatusResponse;

    PostStatusServiceProxy postStatusService;
    PostStatusPresenter postStatusPresenter;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        AuthToken authToken = new AuthToken("<mockToken>", "test");
        String message = "mock post .";
        LocalDateTime timeStamp = LocalDateTime.parse("2021-01-01T00:00:00");
        User user = new User("First", "Last", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        Status status = new Status(timeStamp, message, user);

        postStatusRequest = new PostStatusRequest(authToken.getToken(), message);
        postStatusResponse = new PostStatusResponse(status);

        postStatusService = Mockito.mock(PostStatusServiceProxy.class);
        Mockito.when(postStatusService.sendStatus(postStatusRequest)).thenReturn(postStatusResponse);

        postStatusPresenter = Mockito.spy(new PostStatusPresenter(new PostStatusPresenter.Fragment() {
            @Override
            public void requestClose() {
            }

            @Override
            public void requestSendTweet(String message) {
            }
        }));
        Mockito.when(postStatusPresenter.getPostStatusService()).thenReturn(postStatusService);
    }

    @Test
    public void testPostStatus_returnsServiceResult() throws IOException, TweeterRemoteException {
        Mockito.when(postStatusService.sendStatus(postStatusRequest)).thenReturn(postStatusResponse);
        assertEquals(postStatusResponse, postStatusPresenter.sendStatus(postStatusRequest));
    }

    @Test
    public void testPostStatus_throwsException() throws IOException, TweeterRemoteException {
        Mockito.when(postStatusService.sendStatus(postStatusRequest)).thenThrow(new IOException());
        assertThrows(IOException.class, () -> {
            postStatusPresenter.sendStatus(postStatusRequest);
        });
    }
}
