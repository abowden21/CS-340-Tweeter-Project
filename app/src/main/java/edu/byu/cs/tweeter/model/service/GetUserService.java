package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.GetUserRequest;
import edu.byu.cs.tweeter.model.service.response.GetUserResponse;

public class GetUserService extends ServiceBase {
    public GetUserResponse getUser(GetUserRequest request) throws IOException {
        GetUserResponse response = getServerFacade().getUser(request);

        return response;
    }
}
