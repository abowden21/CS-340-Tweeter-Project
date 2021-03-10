package edu.byu.cs.tweeter.client.model.service;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;

import edu.byu.cs.tweeter.shared.model.domain.User;
import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.shared.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.model.request.LoginRequest;
import edu.byu.cs.tweeter.shared.model.request.LogoutRequest;
import edu.byu.cs.tweeter.shared.model.request.RegisterRequest;
import edu.byu.cs.tweeter.shared.model.response.LoginResponse;
import edu.byu.cs.tweeter.shared.model.response.LogoutResponse;
import edu.byu.cs.tweeter.shared.model.response.RegisterResponse;
import edu.byu.cs.tweeter.client.util.ByteArrayUtils;
import edu.byu.cs.tweeter.shared.model.service.LoginServiceInterface;

/**
 * Contains the business logic to support the login operation.
 */
public class LoginServiceProxy extends ServiceBase implements LoginServiceInterface {

    private static final String URL_PATH = "/auth/login";

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LoginResponse login(LoginRequest request) throws IOException, TweeterRemoteException {
        ServerFacade serverFacade = getServerFacade();
        LoginResponse loginResponse = serverFacade.login(request, URL_PATH);

        if(loginResponse.isSuccess()) {
            loadImage(loginResponse.getUser());
        }

        return loginResponse;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public RegisterResponse register(RegisterRequest request) throws IOException {
        ServerFacade serverFacade = getServerFacade();
        RegisterResponse registerResponse = serverFacade.register(request);

        if(registerResponse.isSuccess()) {
            loadImage(registerResponse.getUser());
        }

        return registerResponse;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LogoutResponse logout(LogoutRequest request) throws IOException {
        ServerFacade serverFacade = getServerFacade();
        return serverFacade.logout(request);
    }

    /**
     * Loads the profile image data for the user.
     *
     * @param user the user whose profile image data is to be loaded.
     */
    private void loadImage(User user) throws IOException {
        byte [] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
        user.setImageBytes(bytes);
    }
}
