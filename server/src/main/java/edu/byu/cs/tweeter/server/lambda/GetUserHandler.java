package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.server.service.GetUserServiceImpl;
import edu.byu.cs.tweeter.shared.model.request.GetUserRequest;
import edu.byu.cs.tweeter.shared.model.response.GetUserResponse;

// Lambda handler:  edu.byu.cs.tweeter.server.lambda.GetUserHandler::handleRequest

public class GetUserHandler implements RequestHandler<GetUserRequest, GetUserResponse> {

    @Override
    public GetUserResponse handleRequest(GetUserRequest getUserRequest, Context context) {
        GetUserServiceImpl getUserService = new GetUserServiceImpl();
        return getUserService.getUser(getUserRequest);
    }
}
