package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.server.service.FollowServiceImpl;
import edu.byu.cs.tweeter.shared.model.request.FollowRequest;
import edu.byu.cs.tweeter.shared.model.response.FollowResponse;

public class SetFollowHandler implements RequestHandler<FollowRequest, FollowResponse> {
    @Override
    public FollowResponse handleRequest(FollowRequest request, Context context) {
        FollowServiceImpl service = new FollowServiceImpl();
        return service.setFollow(request);
    }
}