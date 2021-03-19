package edu.byu.cs.tweeter.client.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import edu.byu.cs.tweeter.shared.model.domain.User;
import edu.byu.cs.tweeter.client.model.service.FollowersServiceProxy;
import edu.byu.cs.tweeter.shared.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.model.request.FollowersRequest;
import edu.byu.cs.tweeter.shared.model.response.FollowersResponse;

public class FollowersPresenterTest {

    private FollowersRequest request;
    private FollowersResponse response;
    private FollowersServiceProxy mMockFollowersServiceProxy;
    private FollowersPresenter presenter;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null);

        User resultUser1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User resultUser2 = new User("FirstName2", "LastName2",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
        User resultUser3 = new User("FirstName3", "LastName3",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");

        request = new FollowersRequest(currentUser.getAlias(), 3, null);
        response = new FollowersResponse(Arrays.asList(resultUser1, resultUser2, resultUser3), false);

        // Create a mock FollowersService
        mMockFollowersServiceProxy = Mockito.mock(FollowersServiceProxy.class);
        Mockito.when(mMockFollowersServiceProxy.getFollowers(request)).thenReturn(response);

        // Wrap a FollowersPresenter in a spy that will use the mock service.
        presenter = Mockito.spy(new FollowersPresenter(new FollowersPresenter.View() {}));
        Mockito.when(presenter.getFollowersService()).thenReturn(mMockFollowersServiceProxy);
    }

    @Test
    public void testGetFollowing_returnsServiceResult() throws IOException, TweeterRemoteException {
        Mockito.when(mMockFollowersServiceProxy.getFollowers(request)).thenReturn(response);

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(response, presenter.getFollowers(request));
    }

    @Test
    public void testGetFollowing_serviceThrowsIOException_presenterThrowsIOException() throws IOException, TweeterRemoteException {
        Mockito.when(mMockFollowersServiceProxy.getFollowers(request)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> {
            presenter.getFollowers(request);
        });
    }
}
