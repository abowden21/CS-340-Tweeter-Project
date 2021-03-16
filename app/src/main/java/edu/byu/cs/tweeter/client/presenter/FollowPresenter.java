package edu.byu.cs.tweeter.client.presenter;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.FollowServiceProxy;
import edu.byu.cs.tweeter.shared.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.model.request.FollowRequest;
import edu.byu.cs.tweeter.shared.model.request.FollowStatusRequest;
import edu.byu.cs.tweeter.shared.model.response.FollowResponse;
import edu.byu.cs.tweeter.shared.model.response.FollowStatusResponse;


public class FollowPresenter {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public FollowResponse setFollow(FollowRequest request) throws IOException, TweeterRemoteException {
        FollowServiceProxy followServiceProxy = getFollowService();
        return followServiceProxy.setFollow(request);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public FollowStatusResponse getFollowStatus(FollowStatusRequest request) throws IOException, TweeterRemoteException {
        FollowServiceProxy followServiceProxy = getFollowService();
        return followServiceProxy.getFollowStatus(request);
    }

    FollowServiceProxy getFollowService() {
        return new FollowServiceProxy();
    }
}
