package edu.byu.cs.tweeter.client.presenter;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.FollowServiceProxy;
import edu.byu.cs.tweeter.shared.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.model.request.UserFollowCountRequest;
import edu.byu.cs.tweeter.shared.model.response.UserFollowCountResponse;

public class GetFollowCountPresenter {

    private View view ;

    public interface View { }

    public GetFollowCountPresenter(){}

    public GetFollowCountPresenter(View view) {
        this.view = view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public UserFollowCountResponse getUserFollowCount(UserFollowCountRequest request) throws IOException, TweeterRemoteException {
        return getFollowService().getUserFollowCount(request);
    }

    FollowServiceProxy getFollowService() {
        return new FollowServiceProxy();
    }

}
