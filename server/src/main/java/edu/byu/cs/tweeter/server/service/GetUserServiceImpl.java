package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.server.DataAccessException;
import edu.byu.cs.tweeter.server.dao.UserDAO;
import edu.byu.cs.tweeter.shared.model.domain.User;
import edu.byu.cs.tweeter.shared.model.request.GetUserRequest;
import edu.byu.cs.tweeter.shared.model.response.GetUserResponse;
import edu.byu.cs.tweeter.shared.model.service.GetUserServiceInterface;

public class GetUserServiceImpl implements GetUserServiceInterface {

    private UserDAO userDao;

    @Override
    public GetUserResponse getUser(GetUserRequest request) {
        User user = null;
        try {
            user = getUserDao().getUser(request.getAlias());
            GetUserResponse response = new GetUserResponse(user);
            return response;
        } catch (DataAccessException e) {
            return new GetUserResponse("Error: failed to retrieve user.");
        }
    }

    public UserDAO getUserDao() {
        if (userDao == null) {
            userDao = new UserDAO();
        }
        return userDao;
    }
}
