package edu.byu.cs.tweeter.shared.model.response;

import edu.byu.cs.tweeter.shared.model.domain.AuthToken;
import edu.byu.cs.tweeter.shared.model.domain.User;

public class RegisterResponse extends LoginResponse {
    public RegisterResponse(){}
    public RegisterResponse(User user, AuthToken authToken) {
        super(user, authToken);
    }
    public RegisterResponse(String message) {
        super( message);
    }
}
