package edu.byu.cs.tweeter.client.model.net;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.byu.cs.tweeter.BuildConfig;
import edu.byu.cs.tweeter.shared.model.domain.AuthToken;
import edu.byu.cs.tweeter.shared.model.domain.Status;
import edu.byu.cs.tweeter.shared.model.domain.User;
import edu.byu.cs.tweeter.shared.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.model.request.FeedRequest;
import edu.byu.cs.tweeter.shared.model.request.FollowRequest;
import edu.byu.cs.tweeter.shared.model.request.FollowStatusRequest;
import edu.byu.cs.tweeter.shared.model.request.FollowersRequest;
import edu.byu.cs.tweeter.shared.model.request.FollowingRequest;
import edu.byu.cs.tweeter.shared.model.request.GetUserRequest;
import edu.byu.cs.tweeter.shared.model.request.LoginRequest;
import edu.byu.cs.tweeter.shared.model.request.LogoutRequest;
import edu.byu.cs.tweeter.shared.model.request.PostStatusRequest;
import edu.byu.cs.tweeter.shared.model.request.RegisterRequest;
import edu.byu.cs.tweeter.shared.model.request.StoryRequest;
import edu.byu.cs.tweeter.shared.model.request.UserFollowCountRequest;
import edu.byu.cs.tweeter.shared.model.response.FeedResponse;
import edu.byu.cs.tweeter.shared.model.response.FollowResponse;
import edu.byu.cs.tweeter.shared.model.response.FollowStatusResponse;
import edu.byu.cs.tweeter.shared.model.response.FollowersResponse;
import edu.byu.cs.tweeter.shared.model.response.FollowingResponse;
import edu.byu.cs.tweeter.shared.model.response.UserFollowCountResponse;
import edu.byu.cs.tweeter.shared.model.response.GetUserResponse;
import edu.byu.cs.tweeter.shared.model.response.LoginResponse;
import edu.byu.cs.tweeter.shared.model.response.LogoutResponse;
import edu.byu.cs.tweeter.shared.model.response.PostStatusResponse;
import edu.byu.cs.tweeter.shared.model.response.RegisterResponse;
import edu.byu.cs.tweeter.shared.model.response.StoryResponse;

/**
 * Acts as a Facade to the Tweeter server. All network requests to the server should go through
 * this class.
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class ServerFacade {

    private static final String SERVER_URL = "https://uv5xi7355k.execute-api.us-west-2.amazonaws.com/production";

    private final ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);

    /**
     * Performs a login and if successful, returns the logged in user and an auth token.
     *
     * @param request contains all information needed to perform a login.
     * @return the login response.
     */
    public LoginResponse login(LoginRequest request, String urlPath) throws IOException, TweeterRemoteException {
        LoginResponse response = clientCommunicator.doPost(urlPath, request, null, LoginResponse.class);

        if(response.isSuccess()) {
            return response;
        } else {
            throw new TweeterRemoteException(response.getMessage());
        }
    }

    public RegisterResponse register(RegisterRequest request, String urlPath) throws IOException, TweeterRemoteException {
        RegisterResponse response = clientCommunicator.doPost(urlPath, request, null, RegisterResponse.class);

        if(response.isSuccess()) {
            return response;
        } else {
            throw new TweeterRemoteException(response.getMessage());
        }
    }

    public LogoutResponse logout(LogoutRequest request, String urlPath) throws IOException, TweeterRemoteException {
        LogoutResponse response = clientCommunicator.doPost(urlPath, request, null, LogoutResponse.class);

        if(response.isSuccess()) {
            return response;
        } else {
            throw new TweeterRemoteException(response.getMessage());
        }
    }

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request.
     *
     * @param request contains information about the user whose followees are to be returned and any
     *                other information required to satisfy the request.
     * @return the followees.
     */
    public FollowingResponse getFollowees(FollowingRequest request, String urlPath)
            throws IOException, TweeterRemoteException {
        FollowingResponse response = clientCommunicator.doPost(urlPath, request, null, FollowingResponse.class);

        if(response.isSuccess()) {
            return response;
        } else {
            throw new TweeterRemoteException(response.getMessage());
        }
    }

    public FollowersResponse getFollowers(FollowersRequest request, String urlPath)
            throws IOException, TweeterRemoteException {
        FollowersResponse response = clientCommunicator.doPost(urlPath, request, null, FollowersResponse.class);

        if(response.isSuccess()) {
            return response;
        } else {
            throw new TweeterRemoteException(response.getMessage());
        }
    }

    public FollowResponse setFollow(FollowRequest request, String urlPath) throws IOException, TweeterRemoteException {
        FollowResponse response = clientCommunicator.doPost(urlPath, request, null, FollowResponse.class);

        if(response.isSuccess()) {
            return response;
        } else {
            throw new TweeterRemoteException(response.getMessage());
        }
    }

    public UserFollowCountResponse getUserFollowCount(UserFollowCountRequest request, String urlPath) throws IOException, TweeterRemoteException {
        UserFollowCountResponse response = clientCommunicator.doPost(urlPath, request, null, UserFollowCountResponse.class);

        if(response.isSuccess()) {
            return response;
        } else {
            throw new TweeterRemoteException(response.getMessage());
        }
    }

    public FollowStatusResponse getFollowStatus(FollowStatusRequest request, String urlPath) throws IOException, TweeterRemoteException {
        FollowStatusResponse response = clientCommunicator.doPost(urlPath, request, null, FollowStatusResponse.class);

        if(response.isSuccess()) {
            return response;
        } else {
            throw new TweeterRemoteException(response.getMessage());
        }
    }

    public PostStatusResponse sendStatus(PostStatusRequest postStatusRequest, String urlPath) throws IOException, TweeterRemoteException {
        PostStatusResponse response = clientCommunicator.doPost(urlPath, postStatusRequest, null, PostStatusResponse.class);
        if(response.isSuccess()) {
            return response;
        } else {
            throw new TweeterRemoteException(response.getMessage());
        }
    }

    public StoryResponse getStory(StoryRequest storyRequest, String urlPath) throws IOException, TweeterRemoteException {
        StoryResponse response = clientCommunicator.doPost(urlPath, storyRequest, null, StoryResponse.class);
        if(response.isSuccess()) {
            return response;
        } else {
            throw new TweeterRemoteException(response.getMessage());
        }
    }

    public FeedResponse getFeed(FeedRequest feedRequest, String urlPath) throws IOException, TweeterRemoteException {
        FeedResponse response = clientCommunicator.doPost(urlPath, feedRequest, null, FeedResponse.class);
        if(response.isSuccess()) {
            return response;
        } else {
            throw new TweeterRemoteException(response.getMessage());
        }
    }

    public GetUserResponse getUser(GetUserRequest request, String urlPath) throws IOException, TweeterRemoteException {

        GetUserResponse response = clientCommunicator.doPost(urlPath, request, null, GetUserResponse.class);
        if(response.isSuccess()) {
            return response;
        } else {
            throw new TweeterRemoteException(response.getMessage());
        }

    }
}
