package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.server.DataAccessException;
import edu.byu.cs.tweeter.shared.model.domain.Status;
import edu.byu.cs.tweeter.shared.model.domain.User;

public class StatusDAO extends BaseDynamoDAO {

    UserDAO userDao;
    public StatusDAO() {
        super("status");
        userDao = new UserDAO();
    }

    public void addStatus(Status status){
        // Log for now so we can inspect in CloudWatch
        System.out.println("Status posted " + status.getTimestampString() + ": " + status.getMessage());
    }
//    public Status getStatus(LocalDateTime timestamp)
//    public List<Status> getStatuses(String userAlias, int limit)
//    public void updateStatus(String token, Status authToken) {}
//    public void deleteStatus(String token) {}

    // TODO: solve pagination/identification for this class. (really more of a Milestone 4 issue)
    // Use cursors instead of page numbers.

    // PAGINATION HERE WE COME
    public List<Status> getUserStory(String userAlias, String lastTimestamp, int limit) throws DataAccessException {
        // Note: reference this code when implementing the FeedDAO. Much of it will be the same, but
        // in addition to the three parameters listed above, you will also need the alias of the user who posted the tweet,
        // as that is part of the primary key. Here, since all tweets are posted by the same user,
        // we can reuse the story owner alias as the tweet poster alias.

        // Since we have to pass the user with each status, we'll fetch the user once and store it.
        User user = userDao.getUser(userAlias);

        List<Status> statuses = new ArrayList<>();

        QuerySpec querySpec = new QuerySpec().withHashKey("alias", userAlias).withMaxResultSize(limit);
        if (lastTimestamp != null) {
            querySpec.withExclusiveStartKey("alias", userAlias, "timestamp", lastTimestamp);
        }
        for (Item item : getTable().query(querySpec)) {
            statuses.add(itemToStatus(item, user));
        }

        return statuses;
    }

    private Status itemToStatus(Item item, User user) {
        return new Status(item.getString("timestamp"), item.getString("message"), user);
    }
}
