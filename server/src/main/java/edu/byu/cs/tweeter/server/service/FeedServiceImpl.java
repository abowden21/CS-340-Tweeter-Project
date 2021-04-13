package edu.byu.cs.tweeter.server.service;

import java.util.List;

import edu.byu.cs.tweeter.server.DataAccessException;
import edu.byu.cs.tweeter.server.dao.AuthTokenDAO;
import edu.byu.cs.tweeter.server.dao.FeedDAO;
import edu.byu.cs.tweeter.shared.model.domain.Status;
import edu.byu.cs.tweeter.shared.model.request.FeedRequest;
import edu.byu.cs.tweeter.shared.model.response.FeedResponse;

public class FeedServiceImpl {

    public FeedResponse getFeed(FeedRequest feedRequest) throws DataAccessException {
        // Copying the same little hack that was used in StoryServiceImpl
        List<Status> statuses = getFeedDAO().getFeed(feedRequest.getAuthToken().getUserAlias(), feedRequest.getTimestampString(), feedRequest.getLimit() + 1);

        System.out.println("Statuses: " + statuses);

        boolean hasMore = false;
        if (statuses.size() > feedRequest.getLimit()) {
            hasMore = true;
            statuses.remove(statuses.size() - 1);
        }

        FeedResponse feedResponse = new FeedResponse(statuses, hasMore);
        return feedResponse;
    }

    FeedDAO getFeedDAO() {
        return new FeedDAO();
    }
}
