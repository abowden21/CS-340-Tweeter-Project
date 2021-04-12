package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.document.BatchWriteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.dynamodbv2.document.spec.BatchWriteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.byu.cs.tweeter.shared.model.domain.Feed;
import edu.byu.cs.tweeter.shared.model.domain.Status;
import edu.byu.cs.tweeter.shared.model.domain.User;
import edu.byu.cs.tweeter.shared.model.request.FeedRequest;
import edu.byu.cs.tweeter.shared.model.response.FeedResponse;

public class FeedDAO extends BaseDynamoDAO {


    public FeedDAO() {
        super("feed");
    }

    public void addStatusToFeed(String feedOwnerAlias, String statusOwnerAlias, String statusTimestamp) {
        // call the other method with list of one
    }

    public void batchAddStatusToFeed(List<String> feedOwnerAliases, String statusOwnerAlias, String statusTimestamp) {

        //DynamoDb: find row matching feedOwnerAlias
        //Construct request object with statusOnwerAlias and statusTimestamp (add to existing list)

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

    public FeedResponse getFeed(FeedRequest request) {
        return null;

        // TODO lots of stuff
    }

}
