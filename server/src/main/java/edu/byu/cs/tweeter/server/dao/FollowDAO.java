package edu.byu.cs.tweeter.server.dao;
import com.amazonaws.services.dynamodbv2.document.BatchWriteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Index;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.dynamodbv2.model.WriteRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import edu.byu.cs.tweeter.shared.model.request.FollowRequest;
import edu.byu.cs.tweeter.shared.model.request.FollowStatusRequest;
import edu.byu.cs.tweeter.shared.model.request.FollowersRequest;
import edu.byu.cs.tweeter.shared.model.request.UserFollowCountRequest;
import edu.byu.cs.tweeter.shared.model.response.FollowResponse;
import edu.byu.cs.tweeter.shared.model.response.FollowStatusResponse;
import edu.byu.cs.tweeter.shared.model.response.UserFollowCountResponse;

public class FollowDAO extends BaseDynamoDAO {

    private final String followeeHandleAttribute = "followee_handle";
    private final String followerHandleAttribute = "follower_handle";

    public FollowDAO() {
        super("follows");
    }

    public FollowResponse setFollow(FollowRequest request) {
        getTable().putItem(new Item().withPrimaryKey(followerHandleAttribute,
                request.getUserAlias(), followeeHandleAttribute, request.getFollowerAlias()));

        return new FollowResponse(true, true);
    }

    public FollowResponse setUnfollow(FollowRequest request) {
        DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
                .withPrimaryKey(new PrimaryKey(followerHandleAttribute,
                        request.getUserAlias(), followeeHandleAttribute, request.getFollowerAlias()));


        System.out.println("Attempting a conditional delete...");
        getTable().deleteItem(deleteItemSpec);
        System.out.println("DeleteItem succeeded");

        return new FollowResponse(true, false);
    }

    public FollowStatusResponse getFollowStatus(FollowStatusRequest request) {
        GetItemSpec spec = new GetItemSpec().withPrimaryKey(followerHandleAttribute,
                request.getLoggedInUserAlias(), followeeHandleAttribute, request.getOtherUserAlias());

        Item item = getTable().getItem(spec);

        //No follow relationship exists
        if (item == null) {
            return new FollowStatusResponse(false, true);
        }

        //Follow exists and is correct
        else if (item.getString(followerHandleAttribute).equals(request.getLoggedInUserAlias()) &&
                item.getString(followeeHandleAttribute).equals(request.getOtherUserAlias())) {
            return new FollowStatusResponse(true, true);
        }

        return new FollowStatusResponse(false, false);
    }

    public UserFollowCountResponse getUserFollowCount(UserFollowCountRequest request) {
        int followerCount = getAllFollowerNames(request.getAlias()).size();
        int followeeCount = getAllFolloweeNames(request.getAlias()).size();

        return new UserFollowCountResponse(followerCount, followeeCount);
    }

    public List<String> getAllFollowerNames(String userAlias) {
        List<String> followers = new ArrayList<>();

        Index index = getTable().getIndex("follows_index");
        for (Item item : index.query(
                new QuerySpec().withHashKey(followeeHandleAttribute, userAlias)
                .withAttributesToGet(followerHandleAttribute))) {

            followers.add(item.getString(followerHandleAttribute));
        }

        return followers;
    }

    public List<String> getAllFolloweeNames(String userAlias) {
        List<String> followees = new ArrayList<>();

        for (Item item : getTable().query(
                new QuerySpec().withHashKey(followerHandleAttribute, userAlias)
                        .withAttributesToGet(followeeHandleAttribute))) {

            followees.add(item.getString(followeeHandleAttribute));
        }

        return followees;
    }

    public List<String> getFollowersPaginated(FollowersRequest followersRequest) {
       List<String> followerNames = new ArrayList<>();
       Index index = getTable().getIndex("follows_index");

       QuerySpec querySpec = new QuerySpec().withHashKey(followeeHandleAttribute,
               followersRequest.getFollowerAlias()).withMaxPageSize(followersRequest.getLimit());
       if (followersRequest.getLastFolloweeAlias() != null) {
           querySpec.withExclusiveStartKey(followeeHandleAttribute, followersRequest.getFollowerAlias(), followerHandleAttribute, followersRequest.getLastFolloweeAlias());
       }

       for (Item item : index.query(querySpec)) {
           followerNames.add(item.getString(followerHandleAttribute));
       }
       return followerNames;
    }
    public void addFollowersBatch(List<String> followers, String userToBeFollowed) {

        String tableName = "follows";
        TableWriteItems items = new TableWriteItems(tableName);

        // Add each user into the TableWriteItems object
        for (String f : followers) {
            Item followItem = new Item()
                    .withString(followerHandleAttribute, f)
                    .withString(followeeHandleAttribute, userToBeFollowed);
            items.addItemToPut(followItem);

            // 25 is the maximum number of items allowed in a single batch write.
            // Attempting to write more than 25 items will result in an exception being thrown
            if (items.getItemsToPut() != null && items.getItemsToPut().size() == 25) {
                loopBatchWrite(items);
                items = new TableWriteItems(tableName);
            }
        }

        // Write any leftover items
        if (items.getItemsToPut() != null && items.getItemsToPut().size() > 0) {
            loopBatchWrite(items);
        }
    }

    private void loopBatchWrite(TableWriteItems items) {

        // The 'dynamoDB' object is of type DynamoDB and is declared statically in this example
        BatchWriteItemOutcome outcome = getDatabase().batchWriteItem(items);

        // Check the outcome for items that didn't make it onto the table
        // If any were not added to the table, try again to write the batch
        while (outcome.getUnprocessedItems().size() > 0) {
            Map<String, List<WriteRequest>> unprocessedItems = outcome.getUnprocessedItems();
            outcome = getDatabase().batchWriteItemUnprocessed(unprocessedItems);
        }
    }
}
