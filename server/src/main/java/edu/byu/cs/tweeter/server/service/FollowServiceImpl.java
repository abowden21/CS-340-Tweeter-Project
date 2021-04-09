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
    private String failedMessage = "Client Error: Failed to send status. User may not be authenticated.";
    private String failedAuthTokenInvalidMessage = "Client Error: Failed to send status; auth token invalid.";
    private String failedServerMessage = "Server Error: Server failed";

    @Override
    public FollowResponse setFollow(FollowRequest request) {
        try {
            AuthTokenValidator.checkAuthToken(request.getAuthToken());

            return getFollowDAO().setFollow(request);
        }
        catch (DataAccessException e) {
            System.out.println(e.toString());
            throw new RuntimeException(failedMessage);
        }
        catch (Exception e) {
            System.out.println(e.toString());
            throw new RuntimeException(failedServerMessage);
        }
    }


    public FollowResponse setUnfollow(FollowRequest request) {
        try {
            AuthTokenValidator.checkAuthToken(request.getAuthToken());

            return getFollowDAO().setUnfollow(request);
        }
        catch (DataAccessException e) {
            System.out.println(e.toString());
            throw new RuntimeException(failedMessage);
        }
        catch (Exception e) {
            System.out.println(e.toString());
            throw new RuntimeException(failedServerMessage);
        }
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
