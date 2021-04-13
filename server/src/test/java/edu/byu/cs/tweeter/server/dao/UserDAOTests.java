package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import edu.byu.cs.tweeter.server.DataAccessException;
import edu.byu.cs.tweeter.shared.model.domain.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserDAOTests {
    UserDAO userDAOSpy;
    Table tableSpy;

    @BeforeEach
    void setup() {
        userDAOSpy = Mockito.spy(new UserDAO());
        Table table = userDAOSpy.getTable();
        tableSpy = Mockito.spy(table);
        Mockito.when(userDAOSpy.getTable()).thenReturn(tableSpy);
    }

    @Test
    void test_addUser() throws DataAccessException {
        // Confirm a user can be added
        long timestamp = System.currentTimeMillis();
        User testUser1 = new User("DAO", "Test", "_daotest_" + timestamp, "https://image.com");
        // Part of this test is making sure no exceptions are thrown.
        userDAOSpy.addUser(testUser1, "password");
        // Also verify appropriate methods were called on the table object.
        Mockito.verify(tableSpy).putItem(Mockito.any(Item.class));
    }

    @Test
    void test_getUser() throws DataAccessException {
        // Confirm a user can be retrieved.
        User user = userDAOSpy.getUser("@testUser");
        assertEquals("@testUser", user.getAlias());
        Mockito.verify(tableSpy).getItem(Mockito.anyString(), Mockito.anyString());
    }
}
