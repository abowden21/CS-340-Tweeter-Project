package edu.byu.cs.tweeter.presenter;

import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;

import edu.byu.cs.tweeter.model.service.FollowService;
import edu.byu.cs.tweeter.model.service.request.GetFolloweeCountRequest;
import edu.byu.cs.tweeter.model.service.request.GetFollowerCountRequest;
import edu.byu.cs.tweeter.model.service.response.GetFolloweeCountResponse;
import edu.byu.cs.tweeter.model.service.response.GetFollowerCountResponse;

public class GetFollowCountPresenter {

    private final View view;
    FollowService followService;

    public GetFollowCountPresenter(View view) {
        this.view = view;
        this.followService = new FollowService();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public GetFollowerCountResponse getFollowerCount(GetFollowerCountRequest request) {
        return followService.getFollowerCount(request);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public GetFolloweeCountResponse getFolloweeCount(GetFolloweeCountRequest request) {
        return followService.getFolloweeCount(request);
    }

}
