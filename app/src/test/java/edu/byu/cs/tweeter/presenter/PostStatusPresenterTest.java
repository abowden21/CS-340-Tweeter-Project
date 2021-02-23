package edu.byu.cs.tweeter.presenter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.time.LocalDateTime;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.PostStatusService;
import edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.service.response.PostStatusResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PostStatusPresenterTest {

    PostStatusRequest postStatusRequest;
    PostStatusResponse postStatusResponse;

    PostStatusService postStatusService;
    PostStatusPresenter postStatusPresenter;

    @BeforeEach
    public void setup() throws IOException {
        AuthToken authToken = new AuthToken("<mockToken>");
        String message = "mock post .";
        LocalDateTime timeStamp = LocalDateTime.parse("2021-01-01T00:00:00");
        User user = new User("First", "Last", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        Status status = new Status(timeStamp, message, user);

        postStatusRequest = new PostStatusRequest(authToken, message);
        postStatusResponse = new PostStatusResponse(status);

        postStatusService = Mockito.mock(PostStatusService.class);
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
    public void testPostStatus_returnsServiceResult() throws IOException {
        Mockito.when(postStatusService.sendStatus(postStatusRequest)).thenReturn(postStatusResponse);
        assertEquals(postStatusResponse, postStatusPresenter.sendStatus(postStatusRequest));
    }

    @Test
    public void testPostStatus_throwsException() throws IOException {
        Mockito.when(postStatusService.sendStatus(postStatusRequest)).thenThrow(new IOException());
        assertThrows(IOException.class, () -> {
            postStatusPresenter.sendStatus(postStatusRequest);
        });
    }
}
