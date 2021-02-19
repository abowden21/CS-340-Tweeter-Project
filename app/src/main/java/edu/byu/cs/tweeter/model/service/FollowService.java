package edu.byu.cs.tweeter.model.service;

import android.os.Build;

import androidx.annotation.RequiresApi;

import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.request.FollowStatusRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;
import edu.byu.cs.tweeter.model.service.response.FollowStatusResponse;

public class FollowService extends ServiceBase {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public FollowResponse setFollow(FollowRequest request) {
        return getServerFacade().setFollow(request);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public FollowStatusResponse getFollowStatus(FollowStatusRequest request) {
        return getServerFacade().getFollowStatus(request);
    }
}
