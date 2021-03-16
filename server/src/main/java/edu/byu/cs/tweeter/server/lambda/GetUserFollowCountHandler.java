package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.server.service.FollowServiceImpl;
import edu.byu.cs.tweeter.shared.model.request.UserFollowCountRequest;
import edu.byu.cs.tweeter.shared.model.response.UserFollowCountResponse;

public class GetUserFollowCountHandler implements RequestHandler<UserFollowCountRequest, UserFollowCountResponse> {
    @Override
    public UserFollowCountResponse handleRequest(UserFollowCountRequest request, Context context) {
        FollowServiceImpl service = new FollowServiceImpl();
        return service.getUserFollowCount(request);
    }
}
