package edu.byu.cs.tweeter.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.FollowService;
import edu.byu.cs.tweeter.model.service.request.UserFollowCountRequest;
import edu.byu.cs.tweeter.model.service.response.UserFollowCountResponse;

public class GetUserFollowCountPresenterTest {

    private FollowService mockFollowService;
    private GetFollowCountPresenter presenter;

    private UserFollowCountRequest validUserFollowCountRequest;
    private UserFollowCountResponse successUserFollowCountResponse;

    /**
     * Create a FollowingService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() throws IOException {

        // Setup request objects to use in the tests
        validUserFollowCountRequest = new UserFollowCountRequest("alias");

        // Setup a mock ServerFacade that will return known responses
        successUserFollowCountResponse = new UserFollowCountResponse(100, 20);



        // Create a FollowingService instance and wrap it with a spy that will use the mock service
        mockFollowService = Mockito.mock(FollowService.class);
        Mockito.when(mockFollowService.getUserFollowCount(validUserFollowCountRequest)).thenReturn(successUserFollowCountResponse);


        // Wrap a FollowingPresenter in a spy that will use the mock service.
        presenter = Mockito.spy(new GetFollowCountPresenter(new GetFollowCountPresenter.View() {}));
        Mockito.when(presenter.getFollowService()).thenReturn(mockFollowService);
    }

    @Test
    public void testGetUserFollowCount_returnsServiceResult() throws IOException {

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(successUserFollowCountResponse, presenter.getUserFollowCount(validUserFollowCountRequest));
    }

    @Test
    public void testGetUserFollowCount_serviceThrowsIOException_presenterThrowsIOException() throws IOException {
        Mockito.when(mockFollowService.getUserFollowCount(validUserFollowCountRequest)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> {
            presenter.getUserFollowCount(validUserFollowCountRequest);
        });
    }
}
