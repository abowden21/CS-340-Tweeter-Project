package edu.byu.cs.tweeter.shared.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.shared.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.model.request.LoginRequest;
import edu.byu.cs.tweeter.shared.model.request.LogoutRequest;
import edu.byu.cs.tweeter.shared.model.request.RegisterRequest;
import edu.byu.cs.tweeter.shared.model.response.LoginResponse;
import edu.byu.cs.tweeter.shared.model.response.LogoutResponse;
import edu.byu.cs.tweeter.shared.model.response.RegisterResponse;

public interface LoginServiceInterface {

    LoginResponse login(LoginRequest request) throws IOException, TweeterRemoteException;
    LogoutResponse logout(LogoutRequest request) throws IOException, TweeterRemoteException;
    RegisterResponse register(RegisterRequest request) throws IOException, TweeterRemoteException;
}
