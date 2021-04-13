package edu.byu.cs.tweeter.server.dao;
import com.amazonaws.services.dynamodbv2.document.BatchWriteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.dynamodbv2.model.WriteRequest;

import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.server.DataAccessException;
import edu.byu.cs.tweeter.shared.model.domain.User;

public class UserDAO extends BaseDynamoDAO {

    public UserDAO() {
        super("user");
    }

    User itemToUser(Item item) {
        return new User(item.getString("firstName"),
                item.getString("lastName"),
                item.getString("alias"),
                item.getString("imageUrl"));
    }

    public void addUser(User user, String hashedPassword) throws DataAccessException {
        Item existingUser = getTable().getItem("alias", user.getAlias());
        if (existingUser != null)
            throw new DataAccessException("Username taken.");
        Item userItem = new Item()
                .withString("alias", user.getAlias())
                .withString("firstName", user.getFirstName())
                .withString("lastName", user.getLastName())
                .withString("imageUrl", user.getImageUrl());
        if (hashedPassword != null)
            userItem = userItem.withString("password", hashedPassword);
        getTable().putItem(userItem);
    }

    public void addUser(User user) throws DataAccessException {
        addUser(user, null);
    }

    public User getUser(String alias) throws DataAccessException {
        Item item = getTable().getItem("alias", alias);
        if (item != null) {
            return itemToUser(item);
        }
        return null;
    }

    public String getHashedPassword(String userAlias) throws DataAccessException {
        return getTable().getItem("alias", userAlias).getString("password");
    }



    /* Batch methods are used only in Filler script (for adding 10k users at a time in DynamoDB) */

    public void addUserBatch(List<User> users) {

        String tableName = "user";
        TableWriteItems items = new TableWriteItems(tableName);

        // Add each user into the TableWriteItems object
        for (User user : users) {
            Item userItem = new Item()
                    .withString("alias", user.getAlias())
                    .withString("firstName", user.getFirstName())
                    .withString("lastName", user.getLastName())
                    .withString("imageUrl", user.getImageUrl());
            items.addItemToPut(userItem);

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
