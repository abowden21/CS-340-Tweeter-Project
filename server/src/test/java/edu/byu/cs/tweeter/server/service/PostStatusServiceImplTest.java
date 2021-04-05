package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import edu.byu.cs.tweeter.server.DataAccessException;
import edu.byu.cs.tweeter.server.dao.AuthTokenDAO;
import edu.byu.cs.tweeter.server.dao.StatusDAO;
import edu.byu.cs.tweeter.server.dao.UserDAO;
import edu.byu.cs.tweeter.shared.model.domain.AuthToken;
import edu.byu.cs.tweeter.shared.model.domain.Status;
import edu.byu.cs.tweeter.shared.model.request.PostStatusRequest;
import edu.byu.cs.tweeter.shared.model.response.PostStatusResponse;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PostStatusServiceImplTest {

    PostStatusServiceImpl serviceSpy;
    StatusDAO statusDao;
    AuthTokenDAO authTokenDao;
    UserDAO userDao;

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
        statusDao = Mockito.mock(StatusDAO.class);
        authTokenDao = Mockito.mock(AuthTokenDAO.class);
        userDao = Mockito.mock(UserDAO.class);
        Mockito.when(authTokenDao.getAuthToken("valid_token")).thenReturn(validAuthToken);
        // Mock/spy service
//        serviceSpy = Mockito.spy(PostStatusServiceImpl.class);
//        Mockito.when(serviceSpy.getStatusDao()).thenReturn(statusDao);
//        Mockito.when(serviceSpy.getAuthTokenDao()).thenReturn(authTokenDao);
//        Mockito.when(serviceSpy.getUserDao()).thenReturn(userDao);
    }


    @Test
    void test_deleteme() {
        PostStatusServiceImpl service = new PostStatusServiceImpl();
        PostStatusResponse response = service.sendStatus(validRequest);
        assertTrue(response.isSuccess());
    }

    @Test
    void test_postStatusSuccess() {
        Mockito.doNothing().when(statusDao).addStatus(Mockito.any(Status.class));
        PostStatusResponse response = serviceSpy.sendStatus(validRequest);
        assertTrue(response.isSuccess());
    }

    @Test
    void test_postStatusFail() throws DataAccessException {
        Mockito.doThrow(DataAccessException.class).when(authTokenDao).getAuthToken(invalidToken);
        assertThrows(RuntimeException.class, ()-> serviceSpy.sendStatus(invalidRequest));
    }
}
