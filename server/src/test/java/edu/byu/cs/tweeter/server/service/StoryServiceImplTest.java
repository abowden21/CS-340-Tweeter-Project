package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.server.DataAccessException;
import edu.byu.cs.tweeter.server.dao.StatusDAO;
import edu.byu.cs.tweeter.shared.model.domain.Status;
import edu.byu.cs.tweeter.shared.model.domain.User;
import edu.byu.cs.tweeter.shared.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.model.request.StoryRequest;
import edu.byu.cs.tweeter.shared.model.response.StoryResponse;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.eq;

public class StoryServiceImplTest {
    private StoryRequest validRequest;
    private StoryRequest invalidRequest;
    private StoryServiceImpl storyServiceSpy;
    private StatusDAO statusDAO;

    private String validUser = "validUser";
    private String invalidUser = "invalidUser";

    List<Status> statuses;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException, DataAccessException {
        final User user1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        final Status status1 = new Status(LocalDateTime.parse("2021-02-15T01:01:05"),
                "message1", user1);
        final Status status2 = new Status(LocalDateTime.parse("2021-02-15T01:01:06"),
                "message2", user1);
        statuses = new ArrayList<>();
        statuses.add(status1);
        statuses.add(status2);

        statusDAO = Mockito.mock(StatusDAO.class);

        validRequest = new StoryRequest(validUser, 2, "");
        invalidRequest = new StoryRequest(invalidUser, 0, "");

        Mockito.when(statusDAO.getUserStory(eq(validUser), Mockito.anyString(), Mockito.anyInt())).thenReturn(statuses);
        Mockito.doThrow(RuntimeException.class).when(statusDAO).getUserStory(eq(invalidUser), Mockito.anyString(), Mockito.anyInt());

        storyServiceSpy = Mockito.spy(new StoryServiceImpl());
        Mockito.when(storyServiceSpy.getStatusDao()).thenReturn(statusDAO);
    }

    @Test
    public void testGetStatus_validRequest_successResponse() throws DataAccessException {
        StoryResponse response = storyServiceSpy.getStory(validRequest);
        Assertions.assertEquals(statuses, response.getStatuses());
    }

    @Test
    public void testGetStatus_invalidRequest_failureResponse() {
        assertThrows(RuntimeException.class, () ->  storyServiceSpy.getStory(invalidRequest));
    }
}
