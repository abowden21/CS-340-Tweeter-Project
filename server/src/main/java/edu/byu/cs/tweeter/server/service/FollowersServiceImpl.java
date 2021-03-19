package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.server.dao.FollowersDAO;
import edu.byu.cs.tweeter.shared.model.request.FollowersRequest;
import edu.byu.cs.tweeter.shared.model.response.FollowersResponse;
import edu.byu.cs.tweeter.shared.model.service.FollowersServiceInterface;
import edu.byu.cs.tweeter.shared.model.service.FollowingServiceInterface;
import edu.byu.cs.tweeter.shared.model.request.FollowingRequest;
import edu.byu.cs.tweeter.shared.model.response.FollowingResponse;
import edu.byu.cs.tweeter.server.dao.FollowingDAO;

/**
 * Contains the business logic for getting the users a user is following.
 */
public class FollowersServiceImpl implements FollowersServiceInterface {

    @Override
    public FollowersResponse getFollowers(FollowersRequest request) {
        return getFollowersDAO().getFollowers(request);
    }

    public FollowersDAO getFollowersDAO() {
        return new FollowersDAO();
    }
}
