package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.server.service.PostStatusServiceImpl;
import edu.byu.cs.tweeter.shared.model.request.PostStatusRequest;
import edu.byu.cs.tweeter.shared.model.response.PostStatusResponse;

// S3 Handler String:   edu.byu.cs.tweeter.server.lambda.PostStatusHandler::handleRequest

public class PostStatusHandler implements RequestHandler<PostStatusRequest, PostStatusResponse> {

    @Override
    public PostStatusResponse handleRequest(PostStatusRequest postStatusRequest, Context context) {
        PostStatusServiceImpl postStatusService = new PostStatusServiceImpl();
        return postStatusService.sendStatus(postStatusRequest);
    }
}

