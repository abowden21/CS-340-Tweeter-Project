package edu.byu.cs.tweeter.server.dao;

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

public class FeedDAOTest {
    FeedDAO feedDaoSpy;
    User userMock;
    Table tableSpy;
    String testUserAlias = "@bluedrift";

    @BeforeEach
    void setup() {
        feedDaoSpy = Mockito.spy(new FeedDAO());
        Table table = feedDaoSpy.getTable();
        tableSpy = Mockito.spy(table);
        Mockito.when(feedDaoSpy.getTable()).thenReturn(tableSpy);
        userMock = Mockito.mock(User.class);
        Mockito.when(userMock.getAlias()).thenReturn(testUserAlias);
    }

    @Test
    void test_getFeed() throws DataAccessException {
        List<Status> feed = feedDaoSpy.getFeed(testUserAlias, null, 10);
        assertTrue(feed.size() > 0);
        Mockito.verify(tableSpy).query(Mockito.any(QuerySpec.class));
    }
}
