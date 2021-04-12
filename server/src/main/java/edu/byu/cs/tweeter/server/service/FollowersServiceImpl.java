package edu.byu.cs.tweeter.server.service;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.server.DataAccessException;
import edu.byu.cs.tweeter.server.dao.FollowDAO;
import edu.byu.cs.tweeter.server.dao.UserDAO;
import edu.byu.cs.tweeter.shared.model.domain.User;
import edu.byu.cs.tweeter.shared.model.request.FollowersRequest;
import edu.byu.cs.tweeter.shared.model.response.FollowersResponse;
import edu.byu.cs.tweeter.shared.model.service.FollowersServiceInterface;

/**
 * Contains the business logic for getting the users a user is following.
 */
public class FollowersServiceImpl implements FollowersServiceInterface {

    @Override
    public FollowersResponse getFollowers(FollowersRequest request) {
        request.setLimit(request.getLimit() + 1);
        List<String> followerNames = getFollowDAO().getFollowersPaginated(request);
        List<User> followers = new ArrayList<>();
        boolean hasMore = false;

        for (String alias : followerNames) {
            try {
                User foundUser = getUserDAO().getUser(alias);
                if (foundUser != null) {
                    followers.add(foundUser);
                }

            } catch (DataAccessException e) {
                e.printStackTrace();
            }
        }
        if (followers.size() > request.getLimit() - 1) {
            hasMore = true;
            followerNames.remove(followerNames.size() - 1);
        }
        return new FollowersResponse(followers, hasMore);
    }

    public FollowDAO getFollowDAO() {
        return new FollowDAO();
    }

    public UserDAO getUserDAO() {
        return new UserDAO();
    }
}
