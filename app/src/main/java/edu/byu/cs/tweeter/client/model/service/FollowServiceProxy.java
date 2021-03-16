package edu.byu.cs.tweeter.client.model.service;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;

import edu.byu.cs.tweeter.shared.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.model.request.FollowRequest;
import edu.byu.cs.tweeter.shared.model.request.FollowStatusRequest;
import edu.byu.cs.tweeter.shared.model.request.UserFollowCountRequest;
import edu.byu.cs.tweeter.shared.model.response.FollowResponse;
import edu.byu.cs.tweeter.shared.model.response.FollowStatusResponse;
import edu.byu.cs.tweeter.shared.model.response.UserFollowCountResponse;
import edu.byu.cs.tweeter.shared.model.service.FollowServiceInterface;

public class FollowServiceProxy extends ServiceBase implements FollowServiceInterface {

    private static final String FOLLOW_URL_PATH = "/follow/follow";
    private static final String UNFOLLOW_URL_PATH = "/follow/unfollow";
    private static final String FOLLOW_STATUS_URL_PATH = "/follow/followstatus";
    private static final String USER_FOLLOW_COUNT_URL_PATH = "/follow/getfollowcount";


    @RequiresApi(api = Build.VERSION_CODES.O)
    public FollowResponse setFollow(FollowRequest request) throws IOException, TweeterRemoteException {
        if (request.isFollowRequest()) {
            return getServerFacade().setFollow(request, FOLLOW_URL_PATH);
        }
        else {
            return getServerFacade().setFollow(request, UNFOLLOW_URL_PATH);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public FollowStatusResponse getFollowStatus(FollowStatusRequest request) throws IOException, TweeterRemoteException {
        return getServerFacade().getFollowStatus(request, FOLLOW_STATUS_URL_PATH);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public UserFollowCountResponse getUserFollowCount(UserFollowCountRequest request) throws IOException, TweeterRemoteException {
        return getServerFacade().getUserFollowCount(request, USER_FOLLOW_COUNT_URL_PATH);
    }
}
