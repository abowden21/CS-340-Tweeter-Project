package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.xml.crypto.Data;

import edu.byu.cs.tweeter.server.DataAccessException;
import edu.byu.cs.tweeter.server.dao.AuthTokenDAO;
import edu.byu.cs.tweeter.server.dao.UserDAO;
import edu.byu.cs.tweeter.shared.model.domain.AuthToken;
import edu.byu.cs.tweeter.shared.model.domain.User;
import edu.byu.cs.tweeter.shared.model.request.LoginRequest;
import edu.byu.cs.tweeter.shared.model.request.LogoutRequest;
import edu.byu.cs.tweeter.shared.model.request.RegisterRequest;
import edu.byu.cs.tweeter.shared.model.response.LoginResponse;
import edu.byu.cs.tweeter.shared.model.response.LogoutResponse;
import edu.byu.cs.tweeter.shared.model.response.RegisterResponse;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginServiceImplTest {
    LoginServiceImpl serviceSpy;
    User validUser;
    AuthToken validAuthToken;
    AuthToken invalidAuthToken;
    LoginRequest loginRequestValid;
    LoginRequest loginRequestInvalid;
    RegisterRequest registerRequestValid;
    RegisterRequest registerRequestInvalid;
    LogoutRequest logoutRequestValid;
    LogoutRequest logoutRequestInvalid;

    UserDAO userDao;
    AuthTokenDAO authTokenDao;

    @BeforeEach
    void setup() throws DataAccessException {
        // Objects to use in assertions
        validUser = new User("first", "last", "valid_user", "image_url");
        validAuthToken = new AuthToken("valid_token", "alias");
        invalidAuthToken = new AuthToken("invalid_token", "will throw ex.");
        loginRequestValid = new LoginRequest("valid_user", "valid_pw");
        loginRequestInvalid = new LoginRequest("invalid_user", "invalid_pw");
        registerRequestValid = new RegisterRequest("valid_user", "valid_pw", "valid_first", "valid_last", new byte[0]);
        registerRequestInvalid = new RegisterRequest("invalid_first", "invalid_last", "invalid_username", "invalid_password", new byte[0]);
        logoutRequestValid = new LogoutRequest(validAuthToken);
        logoutRequestInvalid = new LogoutRequest(invalidAuthToken);

        // Mock DAOs
        authTokenDao = Mockito.mock(AuthTokenDAO.class);
        Mockito.when(authTokenDao.getAuthToken("valid_token")).thenReturn(validAuthToken);
        Mockito.doThrow(DataAccessException.class).when(authTokenDao).deleteAuthToken("invalid_token");
        userDao = Mockito.mock(UserDAO.class);
        Mockito.when(userDao.getUser("valid_user")).thenReturn(validUser);
        Mockito.when(userDao.getUser("invalid_user")).thenThrow(DataAccessException.class);
        // Mock/spy for service class
        serviceSpy = Mockito.spy(new LoginServiceImpl());
        Mockito.when(serviceSpy.getAuthTokenDao()).thenReturn(authTokenDao);
        Mockito.when(serviceSpy.getUserDao()).thenReturn(userDao);
    }

    @Test
    void test_loginSuccess() {
        LoginResponse loginResponse = serviceSpy.login(loginRequestValid);
        assertEquals(loginResponse.getUser(), validUser);
    }

    @Test
    void test_loginFail() {
        LoginResponse loginResponse = serviceSpy.login(loginRequestInvalid);
        assertEquals(loginResponse.isSuccess(), false);
    }

    @Test
    void test_registerSuccess() {
        assertDoesNotThrow(() -> {
            RegisterResponse registerResponse = serviceSpy.register(registerRequestValid);
            assertEquals(registerResponse.getUser().getAlias(), validUser.getAlias());
        });
    }

    @Test
    void test_registerFail() throws DataAccessException {
        Mockito.doThrow(DataAccessException.class).when(userDao).addUser(Mockito.any(User.class));
        RegisterResponse registerResponse = serviceSpy.register(registerRequestInvalid);
        assertEquals(registerResponse.isSuccess(), false);
    }

    @Test
    void test_logoutSuccess() {
        LogoutResponse logoutResponse = serviceSpy.logout(logoutRequestValid);
        assertEquals(logoutResponse.isSuccess(), true);
    }

    @Test
    void test_logoutFail() {
        LogoutResponse logoutResponse = serviceSpy.logout(logoutRequestInvalid);
        assertEquals(logoutResponse.isSuccess(), false);
    }
}
