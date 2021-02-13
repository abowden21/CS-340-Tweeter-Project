package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;
import edu.byu.cs.tweeter.util.ByteArrayUtils;

/**
 * Contains the business logic for getting the statuses for the user's story.
 */
public class StoryService extends ServiceBase {

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request. Uses the {@link ServerFacade} to
     * get the followees from the server.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followees.
     */
    public StoryResponse getStory(StoryRequest request) throws IOException {
        StoryResponse response = getServerFacade().getStory(request);

        if(response.isSuccess()) {
            //loadImages(response);
        }

        return response;
    }

    /**
     * Loads the profile image data for each followee included in the response.
     *
     * @param response the response from the followee request.
     */
//    private void loadImages(StoryResponse response) throws IOException {
//        for(Status status : response.getStatuses()) {
//            byte [] bytes = ByteArrayUtils.bytesFromUrl(u.getImageUrl());
//            status.setImageBytes(bytes);
//        }
//    }
}
