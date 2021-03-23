package edu.byu.cs.tweeter.server.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.byu.cs.tweeter.shared.model.domain.Status;
import edu.byu.cs.tweeter.shared.model.domain.User;
import edu.byu.cs.tweeter.shared.model.request.StoryRequest;
import edu.byu.cs.tweeter.shared.model.response.StoryResponse;

public class StoryDAO {
    //Dummy Data from Server Facade
    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";

    private final User user1 = new User("Allen", "Anderson", MALE_IMAGE_URL);
    private final User user2 = new User("Amy", "Ames", FEMALE_IMAGE_URL);
    private final User user3 = new User("Bob", "Bobson", MALE_IMAGE_URL);
    private final User user4 = new User("Bonnie", "Beatty", FEMALE_IMAGE_URL);
    private final User user5 = new User("Chris", "Colston", MALE_IMAGE_URL);
    private final User user6 = new User("Cindy", "Coats", FEMALE_IMAGE_URL);
    private final User user7 = new User("Dan", "Donaldson", MALE_IMAGE_URL);
    private final User user8 = new User("Dee", "Dempsey", FEMALE_IMAGE_URL);
    private final User user9 = new User("Elliott", "Enderson", MALE_IMAGE_URL);
    private final User user10 = new User("Elizabeth", "Engle", FEMALE_IMAGE_URL);
    private final User user11 = new User("Frank", "Frandson", MALE_IMAGE_URL);
    private final User user12 = new User("Fran", "Franklin", FEMALE_IMAGE_URL);
    private final Status status1 = new Status(LocalDateTime.parse("2021-02-13T01:01:01"), "message1", null ,null, user11);
    private final Status status2 = new Status(LocalDateTime.parse("2021-02-14T01:01:02"), "@TacoBell , Check out this cool website I found: www.google.com   How's it going @RyanBryson", null, null, user12);
    private final Status status3 = new Status(LocalDateTime.parse("2021-02-15T01:01:03"), "My daughter just learned she is accepted to BYU!\n" +
            "\n" +
            "That's 3 children from my house. \n" +
            "Plus my wife and I met there, \n" +
            "my parents met there, \n" +
            "and my wife's parents met there. ", null ,null, user1);
    private final Status status4 = new Status(LocalDateTime.parse("2021-02-15T01:01:04"), "message4", null, null, user2);
    private final Status status5 = new Status(LocalDateTime.parse("2021-02-15T01:01:05"), "message5", null ,null, user3);
    private final Status status6 = new Status(LocalDateTime.parse("2021-02-15T01:01:06"), "message6", null, null, user4);
    private final Status status7 = new Status(LocalDateTime.parse("2021-02-15T01:01:07"), "message7", null ,null, user5);
    private final Status status8 = new Status(LocalDateTime.parse("2021-02-15T01:01:08"), "message8", null, null, user6);
    private final Status status9 = new Status(LocalDateTime.parse("2021-02-15T01:01:09"), "message9", null ,null, user7);
    private final Status status10 = new Status(LocalDateTime.parse("2021-02-15T01:01:10"), "message10", null, null, user8);
    private final Status status11 = new Status(LocalDateTime.parse("2021-02-15T01:01:11"), "message11", null ,null, user9);
    private final Status status12 = new Status(LocalDateTime.parse("2021-02-15T01:01:12"), "message12", null, null, user10);


    public StoryResponse getStory(StoryRequest request) {
        if(request.getLimit() < 0) {
            throw new AssertionError();
        }

        if(request.getUserAlias() == null) {
            throw new AssertionError();
        }

        List<Status> allStatuses = getDummyStatuses();
        List<Status> responseStatuses = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            int statusIndex = getStatusesStartingIndex(request.getLastTimestamp(), allStatuses);

            for(int limitCounter = 0; statusIndex < allStatuses.size() && limitCounter < request.getLimit(); statusIndex++, limitCounter++) {
                responseStatuses.add(allStatuses.get(statusIndex));
            }

            hasMorePages = statusIndex < allStatuses.size();
        }

        return new StoryResponse(responseStatuses, hasMorePages);
    }

    List<Status> getDummyStatuses() {
        return Arrays.asList(status1, status2, status3, status4, status5, status6, status7,
                status8, status9, status10, status11, status12);
    }

    private int getStatusesStartingIndex(LocalDateTime lastTimeStamp, List<Status> allStatuses) {

        int statusIndex = 0;

        if(lastTimeStamp != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allStatuses.size(); i++) {
                if(lastTimeStamp.equals(allStatuses.get(i).getTimestamp())) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    statusIndex = i + 1;
                    break;
                }
            }
        }

        return statusIndex;
    }
}
