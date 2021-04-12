package edu.byu.cs.tweeter.server.dao;
import com.amazonaws.services.dynamodbv2.document.Item;

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

//    public void storeHashedPassword(String userAlias, String password) throws DataAccessException {
//
//    }

//    public void updateUser(String alias, User user) throws DataAccessException {}
//
//    public void deleteUser(String alias) throws DataAccessException {}
}
