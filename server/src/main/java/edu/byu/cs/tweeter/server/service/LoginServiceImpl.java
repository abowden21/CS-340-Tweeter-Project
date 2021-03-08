package edu.byu.cs.tweeter.server.service;

import java.io.IOException;

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

    @Override
    public LoginResponse login(LoginRequest request) {

        // TODO: Generates dummy data. Replace with a real implementation.
        User user = new User("Test", "User",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        return new LoginResponse(user, new AuthToken("<DUMMY TOKEN>"));
    }


    //TODO: logout/register
    @Override
    public LogoutResponse logout(LogoutRequest logoutRequest) throws IOException, TweeterRemoteException {
        return null;
    }

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) throws IOException, TweeterRemoteException {
        return null;
    }
}
