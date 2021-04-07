package edu.byu.cs.tweeter.server.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.byu.cs.tweeter.server.DataAccessException;
import edu.byu.cs.tweeter.shared.model.domain.User;

public class UserDaoTests {
    UserDAO userDAO;

    @BeforeEach
    void setup() {
        userDAO = new UserDAO();
    }

    // Warning: all of these persist data.
    // Clean the user table afterwards, manually or with a takedown script.

    @Test
    void test_addUser() throws DataAccessException {
        User testUser1 = new User("John", "Simmons-Test", "john1112", "https://image.com");
        userDAO.addUser(testUser1, "password");
        // warning: not a real test
    }

    @Test
    void test_getUser() throws DataAccessException {
        User user = userDAO.getUser("john1112");
        System.out.println(user.toString());
        // warning: not a real test
    }
}
