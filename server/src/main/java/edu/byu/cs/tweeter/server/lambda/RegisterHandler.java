package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.server.service.LoginServiceImpl;
import edu.byu.cs.tweeter.shared.model.request.RegisterRequest;
import edu.byu.cs.tweeter.shared.model.response.RegisterResponse;

// S3 Handler String:   edu.byu.cs.tweeter.server.lambda.RegisterHandler::handleRequest

public class RegisterHandler implements RequestHandler<RegisterRequest, RegisterResponse> {

    @Override
    public RegisterResponse handleRequest(RegisterRequest registerRequest, Context context) {
        LoginServiceImpl loginService = new LoginServiceImpl();
        return loginService.register(registerRequest);
    }
}
