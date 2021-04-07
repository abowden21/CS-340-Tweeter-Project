package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.server.dao.StoryDAO;
import edu.byu.cs.tweeter.shared.model.request.StoryRequest;
import edu.byu.cs.tweeter.shared.model.response.StoryResponse;

public class StoryServiceImpl {

    public StoryResponse getStory(StoryRequest storyRequest) {
//        return getStoryDAO().getStory(storyRequest);
        return null;
        // TODO: impl
    }

    StoryDAO getStoryDAO() {
        return new StoryDAO();
    }
}
