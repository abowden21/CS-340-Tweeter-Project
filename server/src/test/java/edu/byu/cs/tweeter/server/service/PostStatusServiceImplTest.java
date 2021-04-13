package edu.byu.cs.tweeter.server.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import edu.byu.cs.tweeter.server.DataAccessException;
import edu.byu.cs.tweeter.server.dao.AuthTokenDAO;
import edu.byu.cs.tweeter.server.dao.StatusDAO;
import edu.byu.cs.tweeter.server.dao.UserDAO;
import edu.byu.cs.tweeter.shared.model.domain.AuthToken;
import edu.byu.cs.tweeter.shared.model.domain.Status;
import edu.byu.cs.tweeter.shared.model.domain.User;
import edu.byu.cs.tweeter.shared.model.request.PostStatusRequest;
import edu.byu.cs.tweeter.shared.model.response.PostStatusResponse;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

public class PostStatusServiceImplTest {

    PostStatusServiceImpl serviceSpy;
    StatusDAO statusDao;
    AuthTokenDAO authTokenDao;
    UserDAO userDao;
    AmazonSQS sqsMock;

    String validToken;
    String invalidToken;
    PostStatusRequest validRequest;
    PostStatusRequest invalidRequest;

    @BeforeEach
    void setup() throws DataAccessException {
        // Test objects
        validToken = "valid_token";
        invalidToken = "invalid_token";
        AuthToken validAuthToken = new AuthToken(validToken, "userAlias", 30);
        validRequest = new PostStatusRequest(validToken, "valid_message");
        invalidRequest = new PostStatusRequest(invalidToken, "invalid_message");
        // Mock dao
        authTokenDao = Mockito.mock(AuthTokenDAO.class);
        Mockito.when(authTokenDao.getAuthToken("valid_token")).thenReturn(validAuthToken);
        userDao = Mockito.mock(UserDAO.class);
        Mockito.when(userDao.getUser("userAlias")).thenReturn(new User("First", "Last", "userAlias", "http://image.com"));

        // Mock/spy service
        serviceSpy = Mockito.spy(PostStatusServiceImpl.class);
        Mockito.when(serviceSpy.getAuthTokenDao()).thenReturn(authTokenDao);
        Mockito.when(serviceSpy.getUserDao()).thenReturn(userDao);

        sqsMock = Mockito.mock(AmazonSQS.class);
        Mockito.when(sqsMock.sendMessage(Mockito.any())).thenReturn(null);
        Mockito.when(serviceSpy.getSqs()).thenReturn(sqsMock);

    }

    @Test
    void test_postStatusSuccess() {
        PostStatusResponse response = serviceSpy.sendStatus(validRequest);
        assertTrue(response.isSuccess());
        verify(sqsMock).sendMessage(Mockito.any());
    }

    @Test
    void test_postStatusFail() throws DataAccessException {
        Mockito.doThrow(DataAccessException.class).when(authTokenDao).getAuthToken(invalidToken);
        assertThrows(RuntimeException.class, ()-> serviceSpy.sendStatus(invalidRequest));
    }
}
