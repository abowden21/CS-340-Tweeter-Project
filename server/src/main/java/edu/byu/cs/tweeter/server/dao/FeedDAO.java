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
                new TableWriteItems("feed").withItemsToPut(writeItems));

        BatchWriteItemOutcome outcome = getDatabase().batchWriteItem(writeSpec);
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

}
