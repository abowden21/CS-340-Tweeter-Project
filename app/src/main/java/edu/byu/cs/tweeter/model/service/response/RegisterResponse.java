package edu.byu.cs.tweeter.model.service.response;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;

public class RegisterResponse extends LoginResponse {
    public RegisterResponse(User user, AuthToken authToken) {
        super(user, authToken);
    }
}
