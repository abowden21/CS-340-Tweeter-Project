package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.server.service.FollowServiceImpl;
import edu.byu.cs.tweeter.shared.model.request.FollowStatusRequest;
import edu.byu.cs.tweeter.shared.model.response.FollowStatusResponse;

public class GetFollowStatusHandler implements RequestHandler<FollowStatusRequest, FollowStatusResponse> {
    @Override
    public FollowStatusResponse handleRequest(FollowStatusRequest request, Context context) {
        FollowServiceImpl service = new FollowServiceImpl();
        return service.getFollowStatus(request);
    }
}