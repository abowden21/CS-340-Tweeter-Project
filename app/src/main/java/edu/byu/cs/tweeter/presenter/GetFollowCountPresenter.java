package edu.byu.cs.tweeter.presenter;

import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.FollowService;
import edu.byu.cs.tweeter.model.service.request.UserFollowCountRequest;
import edu.byu.cs.tweeter.model.service.response.UserFollowCountResponse;

public class GetFollowCountPresenter {

    private final View view;

    public interface View {
        // If needed, specify methods here that will be called on the view in response to model updates
    }

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
