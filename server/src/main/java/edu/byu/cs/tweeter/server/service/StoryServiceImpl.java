package edu.byu.cs.tweeter.server.service;

import java.util.List;

import edu.byu.cs.tweeter.server.DataAccessException;
import edu.byu.cs.tweeter.server.dao.StatusDAO;
import edu.byu.cs.tweeter.shared.model.domain.Status;
import edu.byu.cs.tweeter.shared.model.request.StoryRequest;
import edu.byu.cs.tweeter.shared.model.response.StoryResponse;

public class StoryServiceImpl {

    public StoryResponse getStory(StoryRequest storyRequest) throws DataAccessException {
        List<Status> statuses = getStatusDao().getUserStory(storyRequest.getUserAlias(), storyRequest.getLastTimestampString(), storyRequest.getLimit()+1);
        // Weird little hack to determine if there's more in the DB
        // We request one extra, and if it comes, we know there's more
        // If we only get what the original limit was (despite adding 1),
        // there's no more.
        boolean hasMore = false;
        if (statuses.size() > storyRequest.getLimit()) {
            hasMore = true;
            statuses.remove(statuses.size() - 1);
        }
        // Create response object and return
        StoryResponse storyResponse = new StoryResponse(statuses, hasMore);
        return storyResponse;
    }

    StatusDAO getStatusDao() {
        return new StatusDAO();
    }
}
