package edu.byu.cs.tweeter.presenter;

import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;

import edu.byu.cs.tweeter.model.service.FollowService;
import edu.byu.cs.tweeter.model.service.request.UserFollowCountRequest;
import edu.byu.cs.tweeter.model.service.response.UserFollowCountResponse;

public class GetFollowCountPresenter {

    private final View view;
    FollowService followService;

    public GetFollowCountPresenter(View view) {
        this.view = view;
        this.followService = new FollowService();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public UserFollowCountResponse getUserFollowCount(UserFollowCountRequest request) {
        return followService.getUserFollowCount(request);
    }

}
