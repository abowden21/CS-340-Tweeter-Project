package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.shared.model.domain.User;

public class UserDAO {
    public UserDAO() {}

    public void addUser(User user) {}

    public User getUser(String alias) {
        User user = new User("Test", "User",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        return user;
    }

    public void updateUser(String alias, User user) {}
    public void deleteUser(String alias) {}
}
