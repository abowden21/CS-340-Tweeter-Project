package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import edu.byu.cs.tweeter.server.DataAccessException;
import edu.byu.cs.tweeter.shared.model.domain.AuthToken;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AuthTokenDAOTests {

    AuthTokenDAO authTokenDaoSpy;
    Table tableSpy;
    AuthToken testToken1;

    @BeforeEach
    void setup() {
        authTokenDaoSpy = Mockito.spy(new AuthTokenDAO());
        Table table = authTokenDaoSpy.getTable();
        tableSpy = Mockito.spy(table);
        Mockito.when(authTokenDaoSpy.getTable()).thenReturn(tableSpy);
        long timestamp = System.currentTimeMillis();
        testToken1 = new AuthToken("<dao_test_token_" + timestamp + ">", "@testUser");
    }

    @Test
    void test_addAuthToken() throws DataAccessException {
        authTokenDaoSpy.addAuthToken(testToken1);
        Mockito.verify(tableSpy).putItem(Mockito.any(Item.class));
    }

    @Test
    void test_retrieveAuthToken() throws DataAccessException {
        AuthToken token = authTokenDaoSpy.getAuthToken("<test_token>");
        assertNotNull(token);
        Mockito.verify(tableSpy).getItem(Mockito.anyString(), Mockito.anyString());
    }
}
