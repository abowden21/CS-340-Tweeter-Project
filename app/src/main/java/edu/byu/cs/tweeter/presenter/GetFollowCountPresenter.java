package edu.byu.cs.tweeter.presenter;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.FollowService;
import edu.byu.cs.tweeter.model.service.request.UserFollowCountRequest;
import edu.byu.cs.tweeter.model.service.response.UserFollowCountResponse;

public class GetFollowCountPresenter {

    private View view ;

    public interface View { }

    public GetFollowCountPresenter(){}

    public GetFollowCountPresenter(View view) {
        this.view = view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public UserFollowCountResponse getUserFollowCount(UserFollowCountRequest request) throws IOException {
        return getFollowService().getUserFollowCount(request);
    }

    FollowService getFollowService() {
        return new FollowService();
    }

}
