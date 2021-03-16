package edu.byu.cs.tweeter.server.service;

import java.io.IOException;

import edu.byu.cs.tweeter.server.dao.AuthTokenDAO;
import edu.byu.cs.tweeter.server.dao.UserDAO;
import edu.byu.cs.tweeter.shared.model.domain.AuthToken;
import edu.byu.cs.tweeter.shared.model.domain.User;
import edu.byu.cs.tweeter.shared.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.model.request.LogoutRequest;
import edu.byu.cs.tweeter.shared.model.request.RegisterRequest;
import edu.byu.cs.tweeter.shared.model.response.LogoutResponse;
import edu.byu.cs.tweeter.shared.model.response.RegisterResponse;
import edu.byu.cs.tweeter.shared.model.service.LoginServiceInterface;
import edu.byu.cs.tweeter.shared.model.request.LoginRequest;
import edu.byu.cs.tweeter.shared.model.response.LoginResponse;

public class LoginServiceImpl implements LoginServiceInterface {
    AuthTokenDAO authTokenDao;
    UserDAO userDao;

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userDao.getUser(request.getUsername());
        String uniqueToken = "<Dummy unique token>";
        AuthToken authToken = new AuthToken(uniqueToken, user.getAlias());
        getAuthTokenDao().addAuthToken(authToken);
        return new LoginResponse(user, authToken);
    }

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        // Receive image bytes as B64 string, upload to S3, and return the URL
        String imageUrl = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
        //
        User user = new User(registerRequest.getFirstName(), registerRequest.getLastName(),
                registerRequest.getUsername(), imageUrl);
        getUserDao().addUser(user);
        String uniqueToken = "<Dummy unique token>";
        AuthToken authToken = new AuthToken(uniqueToken, user.getAlias());
        getAuthTokenDao().addAuthToken(authToken);
        return new RegisterResponse(user, authToken);
    }

    @Override
    public LogoutResponse logout(LogoutRequest logoutRequest) {
        String token = logoutRequest.getAuthToken().getToken();
        getAuthTokenDao().deleteAuthToken(token);
        return new LogoutResponse(true);
    }

    public AuthTokenDAO getAuthTokenDao() {
        if (this.authTokenDao == null)
            this.authTokenDao = new AuthTokenDAO();
        return this.authTokenDao;
    }

    public UserDAO getUserDao() {
        if (this.userDao == null)
            this.userDao = new UserDAO();
        return this.userDao;
    }
}