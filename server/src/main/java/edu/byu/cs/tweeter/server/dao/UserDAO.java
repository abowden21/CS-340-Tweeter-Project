package edu.byu.cs.tweeter.server.dao;
import com.amazonaws.services.dynamodbv2.document.Item;

import edu.byu.cs.tweeter.server.DataAccessException;
import edu.byu.cs.tweeter.shared.model.domain.User;

public class UserDAO extends BaseDynamoDAO {
    public UserDAO() {
        super("user");
    }

    public void addUser(User user, String hashedPassword) throws DataAccessException {
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
//        User user = new User("Test", "User",
//                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
//        return user;

        return null;
    }

    public String getHashedPassword(String userAlias) throws DataAccessException {
        return null;
    }

//    public void storeHashedPassword(String userAlias, String password) throws DataAccessException {
//
//    }

//    public void updateUser(String alias, User user) throws DataAccessException {}
//
//    public void deleteUser(String alias) throws DataAccessException {}
}
