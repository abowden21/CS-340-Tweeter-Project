package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.server.DataAccessException;
import edu.byu.cs.tweeter.server.dao.FollowDAO;
import edu.byu.cs.tweeter.server.utils.AuthTokenValidator;
import edu.byu.cs.tweeter.shared.model.request.FollowRequest;
import edu.byu.cs.tweeter.shared.model.request.FollowStatusRequest;
import edu.byu.cs.tweeter.shared.model.request.UserFollowCountRequest;
import edu.byu.cs.tweeter.shared.model.response.FollowResponse;
import edu.byu.cs.tweeter.shared.model.response.FollowStatusResponse;
import edu.byu.cs.tweeter.shared.model.response.UserFollowCountResponse;
import edu.byu.cs.tweeter.shared.model.service.FollowServiceInterface;

public class FollowServiceImpl implements FollowServiceInterface {

    @Override
    public FollowResponse setFollow(FollowRequest request) {
        return getFollowDAO().setFollow(request);
    }


    public FollowResponse setUnfollow(FollowRequest request) {
            return getFollowDAO().setUnfollow(request);
    }

    @Override
    public FollowStatusResponse getFollowStatus(FollowStatusRequest request) {
        return getFollowDAO().getFollowStatus(request);
    }

    @Override
    public UserFollowCountResponse getUserFollowCount(UserFollowCountRequest request) {
        return getFollowDAO().getUserFollowCount(request);
    }

    FollowDAO getFollowDAO() {
        return new FollowDAO();
    }
}
