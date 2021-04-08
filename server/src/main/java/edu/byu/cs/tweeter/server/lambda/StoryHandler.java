package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.server.DataAccessException;
import edu.byu.cs.tweeter.server.service.StoryServiceImpl;
import edu.byu.cs.tweeter.shared.model.request.StoryRequest;
import edu.byu.cs.tweeter.shared.model.response.StoryResponse;

public class StoryHandler implements RequestHandler<StoryRequest, StoryResponse> {

    @Override
    public StoryResponse handleRequest(StoryRequest storyRequest, Context context) {
        StoryServiceImpl storyService = new StoryServiceImpl();
        try {
            return storyService.getStory(storyRequest);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
