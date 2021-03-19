package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.server.service.FeedServiceImpl;
import edu.byu.cs.tweeter.shared.model.request.FeedRequest;
import edu.byu.cs.tweeter.shared.model.response.FeedResponse;

public class FeedHandler implements RequestHandler<FeedRequest, FeedResponse> {

    @Override
    public FeedResponse handleRequest(FeedRequest feedRequest, Context context) {
        FeedServiceImpl feedService = new FeedServiceImpl();
        return feedService.getStory(feedRequest);
    }
}
