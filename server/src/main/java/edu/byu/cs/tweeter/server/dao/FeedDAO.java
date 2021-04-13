package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.BatchWriteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.QueryFilter;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.dynamodbv2.document.spec.BatchWriteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;

import org.w3c.dom.Attr;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.byu.cs.tweeter.server.DataAccessException;
import edu.byu.cs.tweeter.shared.model.domain.AuthToken;
import edu.byu.cs.tweeter.shared.model.domain.Feed;
import edu.byu.cs.tweeter.shared.model.domain.Status;
import edu.byu.cs.tweeter.shared.model.domain.User;
import edu.byu.cs.tweeter.shared.model.request.FeedRequest;
import edu.byu.cs.tweeter.shared.model.response.FeedResponse;

public class FeedDAO extends BaseDynamoDAO {

    StatusDAO statusDAO;
    UserDAO userDAO;
    public FeedDAO() {
        super("feed");
        statusDAO = new StatusDAO();
        userDAO = new UserDAO();
    }

    public void addStatusToFeed(String feedOwnerAlias, String statusOwnerAlias, String statusTimestamp) {
        // call the other method with list of one
        List<String> singleFeedOwner = new ArrayList<String>();
        singleFeedOwner.add(feedOwnerAlias);
        batchAddStatusToFeed(singleFeedOwner, statusOwnerAlias, statusTimestamp);
    }

    public void batchAddStatusToFeed(List<String> feedOwnerAliases, String statusOwnerAlias, String statusTimestamp) {

        //DynamoDb: find row matching feedOwnerAlias
        //Construct request object with statusOwnerAlias and statusTimestamp (add to existing list)

        // Batch put a new row for each follower
        List<Item> writeItems =  new ArrayList<Item>();
        for (String feedOwner : feedOwnerAliases) {
            Item item = new Item()
                    .withString("alias", feedOwner)
                    .withString("statusOwnerAlias", statusOwnerAlias)
                    .withString("statusTimestamp", statusTimestamp);
            writeItems.add(item);
        }
        BatchWriteItemSpec writeSpec = new BatchWriteItemSpec().withTableWriteItems(
                new TableWriteItems(getTable().getTableName()).withItemsToPut(writeItems));

        BatchWriteItemOutcome outcome = getDatabase().batchWriteItem(writeSpec);
//        outcome.getBatchWriteItemResult().

    }

    public List<Status> getFeed(String userAlias, String lastTimestamp, int limit) throws DataAccessException {
        // Get the "statuses" in that user's feed from the Feed Table (These aren't Status objects,
        // just objects with a timestamp and corresponding user for each status)
        ArrayList<String> feedStatusTimestamps = new ArrayList<>();
        ArrayList<String> feedStatusAliases = new ArrayList<>();

        System.out.println("lastTimeStamp: " + lastTimestamp);
        System.out.println("Getting feed statuses for user: " + userAlias);

        QuerySpec feedQuerySpec = new QuerySpec().withHashKey("alias", userAlias).withMaxResultSize(limit).withScanIndexForward(false);
        if (lastTimestamp != null) {
            feedQuerySpec.withExclusiveStartKey("alias", userAlias, "statusTimestamp", lastTimestamp);
        }
        for (Item item : getTable().query(feedQuerySpec)) {
            System.out.println("Item from feed table: " + item);
            feedStatusTimestamps.add(item.getString("statusTimestamp"));
            feedStatusAliases.add(item.getString("statusOwnerAlias"));
        }

        // Using the collection of objects from the Feed Table we just got, query the Status table
        // to get the actual Status object

        System.out.println("Getting statuses from status table using: \n" + feedStatusTimestamps);

        List<Status> statuses = new ArrayList<>();

        for (int i = 0; i < feedStatusTimestamps.size(); ++i) {
            String statusTimestamp = feedStatusTimestamps.get(i);
            String statusAlias = feedStatusAliases.get(i);
            User user = userDAO.getUser(statusAlias);
            System.out.println("User: " + user);

            Item item = statusDAO.getTable().getItem("alias", statusAlias, "timestamp", statusTimestamp);

            System.out.println("Item Retrieved: " + item);

            statuses.add(itemToStatus(item, user));
        }
        return statuses;
    }

    private Status itemToStatus(Item item, User user) {
        return new Status(item.getString("timestamp"), item.getString("message"), user);
    }







    // TODO delete:
//    //Dummy Data from Server Facade
//    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
//    private static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";
//
//    private final User user1 = new User("Allen", "Anderson", MALE_IMAGE_URL);
//    private final User user2 = new User("Amy", "Ames", FEMALE_IMAGE_URL);
//    private final User user3 = new User("Bob", "Bobson", MALE_IMAGE_URL);
//    private final User user4 = new User("Bonnie", "Beatty", FEMALE_IMAGE_URL);
//    private final User user5 = new User("Chris", "Colston", MALE_IMAGE_URL);
//    private final User user6 = new User("Cindy", "Coats", FEMALE_IMAGE_URL);
//    private final User user7 = new User("Dan", "Donaldson", MALE_IMAGE_URL);
//    private final User user8 = new User("Dee", "Dempsey", FEMALE_IMAGE_URL);
//    private final User user9 = new User("Elliott", "Enderson", MALE_IMAGE_URL);
//    private final User user10 = new User("Elizabeth", "Engle", FEMALE_IMAGE_URL);
//    private final User user11 = new User("Frank", "Frandson", MALE_IMAGE_URL);
//    private final User user12 = new User("Fran", "Franklin", FEMALE_IMAGE_URL);
//    private final Status status1 = new Status(LocalDateTime.parse("2021-02-13T01:01:01"), "message1", user11);
//    private final Status status2 = new Status(LocalDateTime.parse("2021-02-14T01:01:02"), "@TacoBell , Check out this cool website I found: www.google.com   How's it going @RyanBryson", user12);
//    private final Status status3 = new Status(LocalDateTime.parse("2021-02-15T01:01:03"), "My daughter just learned she is accepted to BYU!\n" +
//            "\n" +
//            "That's 3 children from my house. \n" +
//            "Plus my wife and I met there, \n" +
//            "my parents met there, \n" +
//            "and my wife's parents met there. ", user1);
//    private final Status status4 = new Status(LocalDateTime.parse("2021-02-15T01:01:04"), "message4", user2);
//    private final Status status5 = new Status(LocalDateTime.parse("2021-02-15T01:01:05"), "message5", user3);
//    private final Status status6 = new Status(LocalDateTime.parse("2021-02-15T01:01:06"), "message6", user4);
//    private final Status status7 = new Status(LocalDateTime.parse("2021-02-15T01:01:07"), "message7", user5);
//    private final Status status8 = new Status(LocalDateTime.parse("2021-02-15T01:01:08"), "message8", user6);
//    private final Status status9 = new Status(LocalDateTime.parse("2021-02-15T01:01:09"), "message9", user7);
//    private final Status status10 = new Status(LocalDateTime.parse("2021-02-15T01:01:10"), "message10", user8);
//    private final Status status11 = new Status(LocalDateTime.parse("2021-02-15T01:01:11"), "message11", user9);
//    private final Status status12 = new Status(LocalDateTime.parse("2021-02-15T01:01:12"), "message12", user10);
//
//    public FeedResponse getFeed(FeedRequest request) {
//        // Used in place of assert statements because Android does not support them
//        //if(BuildConfig.DEBUG) {
//            if(request.getLimit() < 0) {
//                throw new AssertionError();
//            }
//
//            if(request.getAuthToken() == null) {
//                throw new AssertionError();
//            }
//        //}
//
//        List<Status> allStatuses = getDummyStatuses();
//        List<Status> responseStatuses = new ArrayList<>(request.getLimit());
//
//        boolean hasMorePages = false;
//
//        if(request.getLimit() > 0) {
//            int statusIndex = getStatusesStartingIndex(request.getLastTimestamp(), allStatuses);
//
//            for(int limitCounter = 0; statusIndex < allStatuses.size() && limitCounter < request.getLimit(); statusIndex++, limitCounter++) {
//                responseStatuses.add(allStatuses.get(statusIndex));
//            }
//
//            hasMorePages = statusIndex < allStatuses.size();
//        }
//
//        return new FeedResponse(responseStatuses, hasMorePages);
//    }
//
//    List<Status> getDummyStatuses() {
//        return Arrays.asList(status1, status2, status3, status4, status5, status6, status7,
//                status8, status9, status10, status11, status12);
//    }
//
//    private int getStatusesStartingIndex(LocalDateTime lastTimeStamp, List<Status> allStatuses) {
//
//        int statusIndex = 0;
//
//        if(lastTimeStamp != null) {
//            // This is a paged request for something after the first page. Find the first item
//            // we should return
//            for (int i = 0; i < allStatuses.size(); i++) {
//                if(lastTimeStamp.toString().equals(allStatuses.get(i).getTimestamp().toString())) {
//                    // We found the index of the last item returned last time. Increment to get
//                    // to the first one we should return
//                    statusIndex = i + 1;
//                    break;
//                }
//            }
//        }
//
//        return statusIndex;
//    }
}
