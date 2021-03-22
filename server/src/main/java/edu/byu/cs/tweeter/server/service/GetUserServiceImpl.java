package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.server.dao.UserDAO;
import edu.byu.cs.tweeter.shared.model.domain.User;
import edu.byu.cs.tweeter.shared.model.request.GetUserRequest;
import edu.byu.cs.tweeter.shared.model.response.GetUserResponse;
import edu.byu.cs.tweeter.shared.model.service.GetUserServiceInterface;

public class GetUserServiceImpl implements GetUserServiceInterface {

    @Override
    public GetUserResponse getUser(GetUserRequest request) {
        UserDAO userDao = new UserDAO();
        User user = userDao.getUser(request.getAlias());
        GetUserResponse response = new GetUserResponse(user);
        return response;
    }
}
