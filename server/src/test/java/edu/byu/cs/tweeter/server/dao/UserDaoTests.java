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

    @Test
    void dev_test() throws DataAccessException {
        User testUser1 = new User("John", "Simmons-Test", "john1112", "https://image.com");
        userDAO.addUser(testUser1);

    }
}
