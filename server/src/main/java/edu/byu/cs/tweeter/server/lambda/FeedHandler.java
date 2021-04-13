package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.server.DataAccessException;
import edu.byu.cs.tweeter.server.service.FeedServiceImpl;
import edu.byu.cs.tweeter.shared.model.request.FeedRequest;
import edu.byu.cs.tweeter.shared.model.response.FeedResponse;

public class FeedHandler implements RequestHandler<FeedRequest, FeedResponse> {

    @Override
    public FeedResponse handleRequest(FeedRequest feedRequest, Context context) {
        FeedServiceImpl feedService = new FeedServiceImpl();
        try {
            return feedService.getFeed(feedRequest);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
