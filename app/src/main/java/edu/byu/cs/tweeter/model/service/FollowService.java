package edu.byu.cs.tweeter.model.service;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.request.FollowStatusRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;
import edu.byu.cs.tweeter.model.service.response.FollowStatusResponse;

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
}
