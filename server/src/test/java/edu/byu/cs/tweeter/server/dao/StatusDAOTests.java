package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import edu.byu.cs.tweeter.server.DataAccessException;
import edu.byu.cs.tweeter.shared.model.domain.Status;
import edu.byu.cs.tweeter.shared.model.domain.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StatusDAOTests {

    StatusDAO statusDAOSpy;
    User userMock;
    Table tableSpy;
    String testUserAlias = "@testUser";

    @BeforeEach
    void setup() {
        statusDAOSpy = Mockito.spy(new StatusDAO());
        Table table = statusDAOSpy.getTable();
        tableSpy = Mockito.spy(table);
        Mockito.when(statusDAOSpy.getTable()).thenReturn(tableSpy);
        userMock = Mockito.mock(User.class);
        Mockito.when(userMock.getAlias()).thenReturn(testUserAlias);
    }

    @Test
    void test_addStatus() {
        // Confirm a status can be added
        Status testStatus1 = new Status("<test_token>", "<dao test message!>", userMock);
        // Part of this test is making sure no exceptions are thrown.
        statusDAOSpy.addStatus(testStatus1);
        // Also verify appropriate methods were called on the table object.
        Mockito.verify(tableSpy).putItem(Mockito.any(Item.class));
    }

    @Test
    void test_getStory() throws DataAccessException {
        // Confirm a status can be retrieved.
        List<Status> story = statusDAOSpy.getUserStory(testUserAlias, null, 10);
        assertTrue(story.size() > 0);
        assertEquals(testUserAlias, story.get(0).getUser().getAlias());
        Mockito.verify(tableSpy).query(Mockito.any(QuerySpec.class));
    }
}
