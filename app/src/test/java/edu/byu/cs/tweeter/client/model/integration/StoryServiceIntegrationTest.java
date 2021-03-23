package edu.byu.cs.tweeter.client.model.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.byu.cs.tweeter.client.model.service.StoryServiceProxy;
import edu.byu.cs.tweeter.shared.model.domain.Status;
import edu.byu.cs.tweeter.shared.model.domain.User;
import edu.byu.cs.tweeter.shared.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.model.request.StoryRequest;
import edu.byu.cs.tweeter.shared.model.response.StoryResponse;

public class StoryServiceIntegrationTest {
    private StoryRequest validRequest;

    private StoryResponse successResponse;

    private List<Status> statuses;

    private StoryServiceProxy storyService;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
        final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";

        final User testUser = new User("FirstName1", "LastName1", null);

        final User user1 = new User("Allen", "Anderson", MALE_IMAGE_URL);
        final User user2 = new User("Amy", "Ames", FEMALE_IMAGE_URL);
        final User user3 = new User("Bob", "Bobson", MALE_IMAGE_URL);
        final User user4 = new User("Bonnie", "Beatty", FEMALE_IMAGE_URL);
        final User user5 = new User("Chris", "Colston", MALE_IMAGE_URL);
        final User user6 = new User("Cindy", "Coats", FEMALE_IMAGE_URL);
        final User user7 = new User("Dan", "Donaldson", MALE_IMAGE_URL);
        final User user8 = new User("Dee", "Dempsey", FEMALE_IMAGE_URL);
        final User user11 = new User("Frank", "Frandson", MALE_IMAGE_URL);
        final User user12 = new User("Fran", "Franklin", FEMALE_IMAGE_URL);

        final Status status1 = new Status(LocalDateTime.parse("2021-02-13T01:01:01"), "message1", null ,null, user11);
        final Status status2 = new Status(LocalDateTime.parse("2021-02-14T01:01:02"), "@TacoBell , Check out this cool website I found: www.google.com   How's it going @RyanBryson", null, null, user12);
        final Status status3 = new Status(LocalDateTime.parse("2021-02-15T01:01:03"), "My daughter just learned she is accepted to BYU!\n" +
                "\n" +
                "That's 3 children from my house. \n" +
                "Plus my wife and I met there, \n" +
                "my parents met there, \n" +
                "and my wife's parents met there. ", null ,null, user1);
        final Status status4 = new Status(LocalDateTime.parse("2021-02-15T01:01:04"), "message4", null, null, user2);
        final Status status5 = new Status(LocalDateTime.parse("2021-02-15T01:01:05"), "message5", null ,null, user3);
        final Status status6 = new Status(LocalDateTime.parse("2021-02-15T01:01:06"), "message6", null, null, user4);
        final Status status7 = new Status(LocalDateTime.parse("2021-02-15T01:01:07"), "message7", null ,null, user5);
        final Status status8 = new Status(LocalDateTime.parse("2021-02-15T01:01:08"), "message8", null, null, user6);
        final Status status9 =  new Status(LocalDateTime.parse("2021-02-15T01:01:09"), "message9", null ,null, user7);
        final Status status10 = new Status(LocalDateTime.parse("2021-02-15T01:01:10"), "message10", null, null, user8);

        statuses = Arrays.asList(status1, status2, status3, status4, status5, status6, status7, status8, status9, status10);

        validRequest = new StoryRequest(testUser.getAlias(), 10, "2021-02-15T01:01:01");
        successResponse = new StoryResponse(statuses, true);
        
        storyService = new StoryServiceProxy();
    }

    @Test
    public void testGetStory_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        StoryResponse response = storyService.getStory(validRequest);

        //No Equal Method for Status so we're comparing messages instead
        for (int i = 0; i < 10; ++i) {
            String expectedMessage = statuses.get(i).getMessage();
            String actualMessage = response.getStatuses().get(i).getMessage();
            Assertions.assertEquals(expectedMessage, actualMessage);
        }
    }

    @Test
    public void testGetStory_validRequest_loadsProfileImages() throws IOException, TweeterRemoteException {
        StoryResponse response = storyService.getStory(validRequest);

        for (Status status: response.getStatuses()) {
            Assertions.assertNotNull(status.getUser().getImageBytes());
        }
    }
}
