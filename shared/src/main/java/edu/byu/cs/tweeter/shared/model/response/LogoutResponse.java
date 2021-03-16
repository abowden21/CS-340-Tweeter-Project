package edu.byu.cs.tweeter.shared.model.response;

public class LogoutResponse extends Response {
    public LogoutResponse(){}
    public LogoutResponse(boolean success) {
        super(success);
    }
    public LogoutResponse(String message) {
        super(false, message);
    }
}
