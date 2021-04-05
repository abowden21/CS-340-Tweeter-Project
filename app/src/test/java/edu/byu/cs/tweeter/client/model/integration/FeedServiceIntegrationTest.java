package edu.byu.cs.tweeter.client.model.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import edu.byu.cs.tweeter.client.model.service.FeedServiceProxy;
import edu.byu.cs.tweeter.shared.model.domain.AuthToken;
import edu.byu.cs.tweeter.shared.model.domain.Status;
import edu.byu.cs.tweeter.shared.model.domain.User;
import edu.byu.cs.tweeter.shared.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.model.request.FeedRequest;
import edu.byu.cs.tweeter.shared.model.response.FeedResponse;

public class FeedServiceIntegrationTest {
    private FeedRequest validRequest;

    private FeedResponse successResponse;

    private List<Status> statuses;

    private FeedServiceProxy feedService;

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

        final Status status1 = new Status(LocalDateTime.parse("2021-02-13T01:01:01"), "message1", user11);
        final Status status2 = new Status(LocalDateTime.parse("2021-02-14T01:01:02"), "@TacoBell , Check out this cool website I found: www.google.com   How's it going @RyanBryson", user12);
        final Status status3 = new Status(LocalDateTime.parse("2021-02-15T01:01:03"), "My daughter just learned she is accepted to BYU!\n" +
                "\n" +
                "That's 3 children from my house. \n" +
                "Plus my wife and I met there, \n" +
                "my parents met there, \n" +
                "and my wife's parents met there. ", user1);
        final Status status4 = new Status(LocalDateTime.parse("2021-02-15T01:01:04"), "message4", user2);
        final Status status5 = new Status(LocalDateTime.parse("2021-02-15T01:01:05"), "message5", user3);
        final Status status6 = new Status(LocalDateTime.parse("2021-02-15T01:01:06"), "message6", user4);
        final Status status7 = new Status(LocalDateTime.parse("2021-02-15T01:01:07"), "message7", user5);
        final Status status8 = new Status(LocalDateTime.parse("2021-02-15T01:01:08"), "message8", user6);
        final Status status9 =  new Status(LocalDateTime.parse("2021-02-15T01:01:09"), "message9", user7);
        final Status status10 = new Status(LocalDateTime.parse("2021-02-15T01:01:10"), "message10", user8);

        statuses = Arrays.asList(status1, status2, status3, status4, status5, status6, status7, status8, status9, status10);

        AuthToken validAuthToken = new AuthToken("validToken", testUser.getAlias());
        validRequest = new FeedRequest(validAuthToken, 10, LocalDateTime.now());
        successResponse = new FeedResponse(statuses, true);

        feedService = new FeedServiceProxy();
    }

    @Test
    public void testFeed_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        FeedResponse response = feedService.getFeed(validRequest);

        //No Equal Method for Status so we're comparing messages instead
        for (int i = 0; i < 10; ++i) {
            String expectedMessage = statuses.get(i).getMessage();
            String actualMessage = response.getStatuses().get(i).getMessage();
            Assertions.assertEquals(expectedMessage, actualMessage);
        }
    }

    @Test
    public void testGetFeed_validRequest_loadsProfileImages() throws IOException, TweeterRemoteException {
        FeedResponse response = feedService.getFeed(validRequest);

        for (Status status: response.getStatuses()) {
            Assertions.assertNotNull(status.getUser().getImageBytes());
        }
    }
}
