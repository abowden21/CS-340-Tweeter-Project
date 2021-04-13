package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.security.NoSuchAlgorithmException;

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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.eq;

public class LoginServiceImplTest {
    LoginServiceImpl serviceSpy;
    User validUser;
    AuthToken validAuthToken;
    AuthToken invalidAuthToken;
    LoginRequest loginRequestValid;
    LoginRequest loginRequestInvalidUser;
    LoginRequest loginRequestInvalidPw;
    RegisterRequest registerRequestValid;
    RegisterRequest registerRequestInvalid;
    LogoutRequest logoutRequestValid;
    LogoutRequest logoutRequestInvalid;

    UserDAO userDaoMock;
    AuthTokenDAO authTokenDaoMock;

    @BeforeEach
    void setup() throws DataAccessException, NoSuchAlgorithmException {
        // Objects to use in assertions
        validUser = new User("first", "last", "valid_user", "image_url");
        validAuthToken = new AuthToken("valid_token", "alias");
        invalidAuthToken = new AuthToken("invalid_token", "will throw ex.");
        loginRequestValid = new LoginRequest("valid_user", "valid_pw");
        loginRequestInvalidUser = new LoginRequest("invalid_user", "valid_pw");
        loginRequestInvalidPw = new LoginRequest("valid_user", "invalid_pw");
        registerRequestValid = new RegisterRequest("valid_user", "valid_pw", "valid_first", "valid_last", new byte[0]);
        registerRequestInvalid = new RegisterRequest("invalid_first", "invalid_last", "invalid_username", "invalid_password", new byte[0]);
        logoutRequestValid = new LogoutRequest(validAuthToken);
        logoutRequestInvalid = new LogoutRequest(invalidAuthToken);

        // Mock DAOs
        authTokenDaoMock = Mockito.mock(AuthTokenDAO.class);
        Mockito.when(authTokenDaoMock.getAuthToken("valid_token")).thenReturn(validAuthToken);
        Mockito.doThrow(DataAccessException.class).when(authTokenDaoMock).deleteAuthToken("invalid_token");
        userDaoMock = Mockito.mock(UserDAO.class);
        Mockito.when(userDaoMock.getUser("valid_user")).thenReturn(validUser);
        Mockito.when(userDaoMock.getUser("invalid_user")).thenThrow(DataAccessException.class);
        Mockito.doNothing().when(userDaoMock).addUser(Mockito.any(User.class));
        // Mock/spy for service class
        serviceSpy = Mockito.spy(new LoginServiceImpl());
        Mockito.when(serviceSpy.getAuthTokenDao()).thenReturn(authTokenDaoMock);
        Mockito.when(serviceSpy.getUserDao()).thenReturn(userDaoMock);
        // Mock secure password manager
        SecurePasswordService spsMock = Mockito.mock(SecurePasswordService.class);
        Mockito.when(spsMock.check(eq("valid_pw"), Mockito.anyString())).thenReturn(true);
        Mockito.when(spsMock.check(eq("invalid_pw"), Mockito.anyString())).thenReturn(false);
        Mockito.when(spsMock.hash(Mockito.anyString())).thenReturn(new SecurePasswordService.SecurePassword(""));
        Mockito.when(serviceSpy.getSecurePasswordService()).thenReturn(spsMock);
    }

    @Test
    void test_loginSuccess() {
        LoginResponse loginResponse = serviceSpy.login(loginRequestValid);
        assertEquals(loginResponse.getUser(), validUser);
    }

    @Test
    void test_loginFailBadPassword() {
        assertThrows(RuntimeException.class, () -> serviceSpy.login(loginRequestInvalidPw));
    }

    @Test
    void test_loginFailBadUser() {
        assertThrows(RuntimeException.class, () -> serviceSpy.login(loginRequestInvalidUser));
    }

    @Test
    void test_registerSuccess() {
        RegisterResponse registerResponse = serviceSpy.register(registerRequestValid);
        assertEquals(registerResponse.getUser().getAlias(), validUser.getAlias());
    }

    @Test
    void test_registerFail() throws DataAccessException {
        Mockito.doThrow(DataAccessException.class).when(userDaoMock).addUser(Mockito.any(User.class), Mockito.anyString());
        assertThrows(RuntimeException.class, () -> serviceSpy.register(registerRequestInvalid));
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
