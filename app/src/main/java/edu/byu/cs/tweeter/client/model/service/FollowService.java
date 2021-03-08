package edu.byu.cs.tweeter.client.model.service;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;

import edu.byu.cs.tweeter.shared.model.request.FollowRequest;
import edu.byu.cs.tweeter.shared.model.request.FollowStatusRequest;
import edu.byu.cs.tweeter.shared.model.request.UserFollowCountRequest;
import edu.byu.cs.tweeter.shared.model.response.FollowResponse;
import edu.byu.cs.tweeter.shared.model.response.FollowStatusResponse;
import edu.byu.cs.tweeter.shared.model.response.UserFollowCountResponse;

public class FollowService extends ServiceBase {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public FollowResponse setFollow(FollowRequest request) throws IOException {
        if (request.isFollowRequest()) {
            return getServerFacade().setFollow(request);
        }
        else {
            return getServerFacade().setUnfollow(request);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public FollowStatusResponse getFollowStatus(FollowStatusRequest request) throws IOException {
        return getServerFacade().getFollowStatus(request);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public UserFollowCountResponse getUserFollowCount(UserFollowCountRequest request) throws IOException {
        return getServerFacade().getUserFollowCount(request);
    }
}
