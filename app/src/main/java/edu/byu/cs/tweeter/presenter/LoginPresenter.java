package edu.byu.cs.tweeter.presenter;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.LoginService;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;

/**
 * The presenter for functionality pertaining to logging in, logging out and registering.
 */
public class LoginPresenter {

    private final View view;
    LoginService loginService;

    public interface View {
        // If needed, specify methods here that will be called on the view in response to model updates
    }

    public LoginPresenter(View view) {
        this.loginService = new LoginService();
        this.view = view;
    }

    /**
     * Makes a login request.
     *
     * @param loginRequest the request.
     */
    public LoginResponse login(LoginRequest loginRequest) throws IOException {
        return loginService.login(loginRequest);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public RegisterResponse register(RegisterRequest registerRequest) throws IOException {
        return loginService.register(registerRequest);
    }

    public LogoutResponse logout(LogoutRequest logoutRequest) throws IOException {
        return loginService.logout(logoutRequest);
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
