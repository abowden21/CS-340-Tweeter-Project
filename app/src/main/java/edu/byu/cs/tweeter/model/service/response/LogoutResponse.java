package edu.byu.cs.tweeter.model.service.response;

public class LogoutResponse extends Response {

    public LogoutResponse(boolean success) {
        super(success);
    }

    public LogoutResponse(String message) {
        super(false, message);
    }
}
