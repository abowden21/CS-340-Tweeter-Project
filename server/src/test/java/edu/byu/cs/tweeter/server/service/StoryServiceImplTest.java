package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;

import edu.byu.cs.tweeter.server.dao.StoryDAO;
import edu.byu.cs.tweeter.shared.model.domain.Status;
import edu.byu.cs.tweeter.shared.model.domain.User;
import edu.byu.cs.tweeter.shared.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.model.request.StoryRequest;
import edu.byu.cs.tweeter.shared.model.response.StoryResponse;

public class StoryServiceImplTest {
    private StoryRequest validRequest;
    private StoryRequest invalidRequest;

    private StoryResponse successResponse;
    private StoryResponse failureResponse;

    private StoryServiceImpl storyServiceSpy;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        final User user1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        final User user2 = new User("FirstName2", "LastName2",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        final Status status1 = new Status(LocalDateTime.parse("2021-02-15T01:01:05"),
                "message1", null ,null, user1);
        final Status status2 = new Status(LocalDateTime.parse("2021-02-15T01:01:06"),
                "message2", null, null, user2);

        validRequest = new StoryRequest(user1.getAlias(), 2, "2021-02-15T01:01:01");
        invalidRequest = new StoryRequest(null, 0, "");

        successResponse = new StoryResponse(Arrays.asList(status1, status2), false);
        StoryDAO storyDAO = Mockito.mock(StoryDAO.class);
        Mockito.when(storyDAO.getStory(validRequest)).thenReturn(successResponse);

        failureResponse = new StoryResponse("An exception occurred");
        Mockito.when(storyDAO.getStory(invalidRequest)).thenReturn(failureResponse);

        storyServiceSpy = Mockito.spy(new StoryServiceImpl());
        Mockito.when(storyServiceSpy.getStoryDAO()).thenReturn(storyDAO);
    }

    @Test
    public void testGetStory_validRequest_successResponse() throws IOException, TweeterRemoteException {
        StoryResponse response = storyServiceSpy.getStory(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testGetStory_invalidRequest_failureResponse() throws IOException, TweeterRemoteException {
        StoryResponse response = storyServiceSpy.getStory(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}
