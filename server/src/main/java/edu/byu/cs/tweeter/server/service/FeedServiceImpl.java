package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.server.dao.FeedDAO;
import edu.byu.cs.tweeter.shared.model.request.FeedRequest;
import edu.byu.cs.tweeter.shared.model.response.FeedResponse;

public class FeedServiceImpl {

    public FeedResponse getStory(FeedRequest feedRequest) {
        return getFeedDAO().getFeed(feedRequest);
    }

    FeedDAO getFeedDAO() {
        return new FeedDAO();
    }
}
