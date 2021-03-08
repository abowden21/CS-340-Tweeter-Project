package edu.byu.cs.tweeter.client.presenter;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.LoginServiceProxy;
import edu.byu.cs.tweeter.shared.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.model.request.LoginRequest;
import edu.byu.cs.tweeter.shared.model.request.LogoutRequest;
import edu.byu.cs.tweeter.shared.model.request.RegisterRequest;
import edu.byu.cs.tweeter.shared.model.response.LoginResponse;
import edu.byu.cs.tweeter.shared.model.response.LogoutResponse;
import edu.byu.cs.tweeter.shared.model.response.RegisterResponse;

/**
 * The presenter for functionality pertaining to logging in, logging out and registering.
 */
public class LoginPresenter {

    private final View view;
    LoginServiceProxy service;

    public interface View { }

    public LoginPresenter(View view) {
        this.service = new LoginServiceProxy();
        this.view = view;
    }


    public LoginServiceProxy getLoginService() {
        return this.service;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LoginResponse login(LoginRequest loginRequest) throws IOException, TweeterRemoteException {
        return getLoginService().login(loginRequest);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public RegisterResponse register(RegisterRequest registerRequest) throws IOException {
        return getLoginService().register(registerRequest);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LogoutResponse logout(LogoutRequest logoutRequest) throws IOException {
        return getLoginService().logout(logoutRequest);
    }

    public boolean validateFields(String username, String password, String firstName, String lastName) {
        // Make sure all fields have at least 1 char
        if (username.length() == 0 || password.length() == 0 || firstName.length() == 0 || lastName.length() == 0) {
            return false;
        }

        // More advanced password requirement => 8-20 characters, at least one number and uppercase
        boolean requireNumAndUppercase = true;

        if (password.length() < 8 || password.length() > 20) {
            // Reject if too short or long
            return false;
        }
        if (requireNumAndUppercase) {
            boolean foundNum = false;
            boolean foundUpper = false;
            for (int i = 0; i < password.length(); i++) {
                if (Character.isDigit(password.charAt(i))) {
                    foundNum = true;
                }
                if (Character.isUpperCase(password.charAt(i))) {
                    foundUpper = true;
                }
            }
            if (!foundNum || !foundUpper) {
                return false;
            }
        }

        return true;
    }
}
