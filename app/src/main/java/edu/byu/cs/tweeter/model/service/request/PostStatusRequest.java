package edu.byu.cs.tweeter.model.service.request;

public class PostStatusRequest {

    private String message;
    // TODO THINK ABOUT WHAT ELSE NEEDS TO BE IN A POSTREQUETS

    public PostStatusRequest() {}
    public PostStatusRequest(String message) {
        this.message = message;
    }
}
