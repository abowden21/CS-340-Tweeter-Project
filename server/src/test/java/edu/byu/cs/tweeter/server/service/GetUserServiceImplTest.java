package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import edu.byu.cs.tweeter.server.DataAccessException;
import edu.byu.cs.tweeter.server.dao.UserDAO;
import edu.byu.cs.tweeter.shared.model.domain.User;
import edu.byu.cs.tweeter.shared.model.request.GetUserRequest;
import edu.byu.cs.tweeter.shared.model.response.GetUserResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class GetUserServiceImplTest {

    User validUser;

    GetUserRequest validRequest;
    GetUserRequest invalidRequest;

    GetUserServiceImpl serviceSpy;
    UserDAO userDaoMock;

    @BeforeEach
    void setup() throws DataAccessException {
        // Mock objects
        String valid_username = "valid_username";
        String invalid_username = "invalid_username";
        validRequest = new GetUserRequest(valid_username);
        invalidRequest = new GetUserRequest(invalid_username);
        validUser = new User("firstName", "lastName", valid_username, "imageUrl");
        // Mock dao
        userDaoMock = Mockito.mock(UserDAO.class);
        Mockito.when(userDaoMock.getUser("valid_username")).thenReturn(validUser);
        Mockito.doThrow(DataAccessException.class).when(userDaoMock).getUser(invalid_username);
        // Mock service
        serviceSpy = Mockito.spy(new GetUserServiceImpl());
        Mockito.when(serviceSpy.getUserDao()).thenReturn(userDaoMock);
    }

    @Test
    void test_getUserSuccess() {
        GetUserResponse response = serviceSpy.getUser(validRequest);
        assertEquals(response.getRetrievedUser(), validUser);
    }

    @Test
    void test_getUserFail() {
        GetUserResponse response = serviceSpy.getUser(invalidRequest);
        assertFalse(response.isSuccess());
    }
}
