package edu.byu.cs.tweeter.client.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;

import edu.byu.cs.tweeter.shared.model.domain.AuthToken;
import edu.byu.cs.tweeter.shared.model.domain.Status;
import edu.byu.cs.tweeter.shared.model.domain.User;
import edu.byu.cs.tweeter.client.model.service.FeedServiceProxy;
import edu.byu.cs.tweeter.shared.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.model.request.FeedRequest;
import edu.byu.cs.tweeter.shared.model.response.FeedResponse;

public class FeedPresenterTest {

    private FeedRequest request;
    private FeedResponse response;
    private FeedServiceProxy mockFeedServiceProxy;
    private FeedPresenter presenter;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User user1 = new User("firstname1", "lastname1", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User user2 = new User("firstname2", "lastname2", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");

        Status resultStatus1 = new Status(LocalDateTime.parse("2021-02-13T01:01:01"), "message1", user1);
        Status resultStatus2 = new Status(LocalDateTime.parse("2021-02-13T01:01:02"), "message2", user1);
        Status resultStatus3 = new Status(LocalDateTime.parse("2021-02-13T01:01:03"), "message3", user2);
        Status resultStatus4 = new Status(LocalDateTime.parse("2021-02-13T01:01:04"), "message4", user2);
        AuthToken validToken = new AuthToken("validToken", "");

        request = new FeedRequest(validToken, 4, "");
        response = new FeedResponse(Arrays.asList(resultStatus1, resultStatus2, resultStatus3, resultStatus4), false);

        mockFeedServiceProxy = Mockito.mock(FeedServiceProxy.class);
        Mockito.when(mockFeedServiceProxy.getFeed(request)).thenReturn(response);

        presenter = Mockito.spy(new FeedPresenter(new FeedPresenter.View() {}));
        Mockito.when(presenter.getFeedService()).thenReturn(mockFeedServiceProxy);
    }

    @Test
    public void testGetFeed_returnsServiceResult() throws IOException, TweeterRemoteException {
        Mockito.when(mockFeedServiceProxy.getFeed(request)).thenReturn(response);

        Assertions.assertEquals(response, presenter.getFeed(request));
    }

    @Test
    public void testGetFeed_serviceThrowsIOException_presenterThrowsIOException() throws IOException, TweeterRemoteException {
        Mockito.when(mockFeedServiceProxy.getFeed(request)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> {
            presenter.getFeed(request);
        });
    }
}
