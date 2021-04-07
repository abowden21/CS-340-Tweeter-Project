package edu.byu.cs.tweeter.server.service;

import java.util.List;

import edu.byu.cs.tweeter.server.dao.FollowDAO;
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
        List<String> followers = getFollowDAO().getFollowers(request.getFollowerAlias());
        return null;
        // TODO: make a response object and return it.
    }

    public FollowDAO getFollowDAO() {
        return new FollowDAO();
    }
}
