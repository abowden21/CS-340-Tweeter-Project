package edu.byu.cs.tweeter.client.presenter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.shared.model.domain.AuthToken;
import edu.byu.cs.tweeter.shared.model.domain.User;
import edu.byu.cs.tweeter.client.model.service.LoginServiceProxy;
import edu.byu.cs.tweeter.shared.model.request.LoginRequest;
import edu.byu.cs.tweeter.shared.model.request.LogoutRequest;
import edu.byu.cs.tweeter.shared.model.request.RegisterRequest;
import edu.byu.cs.tweeter.shared.model.response.LoginResponse;
import edu.byu.cs.tweeter.shared.model.response.LogoutResponse;
import edu.byu.cs.tweeter.shared.model.response.RegisterResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LoginPresenterTest {
    LoginRequest loginRequest;
    LoginResponse loginResponse;

    RegisterRequest registerRequest;
    RegisterResponse registerResponse;

    LogoutRequest logoutRequest;
    LogoutResponse logoutResponse;

    LoginServiceProxy loginService;
    LoginPresenter loginPresenter;

    @BeforeEach
    public void setup() throws IOException {

        User user = new User("first", "last", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        AuthToken authToken = new AuthToken("<mockToken>");

        loginRequest = new LoginRequest("username", "pw");
        loginResponse = new LoginResponse(user, authToken);
        registerRequest = new RegisterRequest("username", "password", "first", "last", new byte[8]);
        registerResponse = new RegisterResponse(user, authToken);
        logoutRequest = new LogoutRequest(authToken);
        logoutResponse = new LogoutResponse(true);

        loginService = Mockito.mock(LoginServiceProxy.class);
        Mockito.when(loginService.login(loginRequest)).thenReturn(loginResponse);
        Mockito.when(loginService.register(registerRequest)).thenReturn(registerResponse);
        Mockito.when(loginService.logout(logoutRequest)).thenReturn(logoutResponse);

        loginPresenter = Mockito.spy(new LoginPresenter(new LoginPresenter.View(){}));
        Mockito.when(loginPresenter.getLoginService()).thenReturn(loginService);
    }

    @Test
    public void testLogin_returnsServiceResult() throws IOException {
        Mockito.when(loginService.login(loginRequest)).thenReturn(loginResponse);
        assertEquals(loginResponse, loginPresenter.login(loginRequest));
    }

    @Test
    public void testLogin_throwsException() throws IOException {
        Mockito.when(loginService.login(loginRequest)).thenThrow(new IOException());
        assertThrows(IOException.class, () -> {
            loginPresenter.login(loginRequest);
        });
    }

    @Test
    public void testRegister_returnsServiceResult() throws IOException {
        Mockito.when(loginService.register(registerRequest)).thenReturn(registerResponse);
        assertEquals(registerResponse, loginPresenter.register(registerRequest));
    }

    @Test
    public void testRegister_throwsException() throws IOException {
        Mockito.when(loginService.register(registerRequest)).thenThrow(new IOException());
        assertThrows(IOException.class, () -> {
            loginPresenter.register(registerRequest);
        });
    }

    @Test
    public void testLogout_returnsServiceResult() throws IOException {
        Mockito.when(loginService.logout(logoutRequest)).thenReturn(logoutResponse);
        assertEquals(logoutResponse, loginPresenter.logout(logoutRequest));
    }

    @Test
    public void testLogout_throwsException() throws IOException {
        Mockito.when(loginService.logout(logoutRequest)).thenThrow(new IOException());
        assertThrows(IOException.class, () -> {
            loginPresenter.logout(logoutRequest);
        });
    }
}
