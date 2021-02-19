package edu.byu.cs.tweeter.presenter;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.FollowService;
import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.request.FollowStatusRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;
import edu.byu.cs.tweeter.model.service.response.FollowStatusResponse;


public class FollowPresenter {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public FollowResponse setFollow(FollowRequest request) throws IOException {
        FollowService followService = getFollowService();
        return followService.setFollow(request);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public FollowStatusResponse getFollowStatus(FollowStatusRequest request) throws IOException {
        FollowService followService = getFollowService();
        return followService.getFollowStatus(request);
    }

    FollowService getFollowService() {
        return new FollowService();
    }
}
