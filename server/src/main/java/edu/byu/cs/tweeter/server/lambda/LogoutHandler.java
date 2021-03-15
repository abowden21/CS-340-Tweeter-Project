package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.server.service.LoginServiceImpl;
import edu.byu.cs.tweeter.shared.model.request.LogoutRequest;
import edu.byu.cs.tweeter.shared.model.response.LogoutResponse;

// S3 Handler String:   edu.byu.cs.tweeter.server.lambda.LogoutHandler::handleRequest

public class LogoutHandler implements RequestHandler<LogoutRequest, LogoutResponse> {

    @Override
    public LogoutResponse handleRequest(LogoutRequest logoutRequest, Context context) {
        LoginServiceImpl loginService = new LoginServiceImpl();
        return loginService.logout(logoutRequest);
    }
}
