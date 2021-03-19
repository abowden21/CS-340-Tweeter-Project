package edu.byu.cs.tweeter.client.presenter;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.FeedServiceProxy;
import edu.byu.cs.tweeter.shared.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.model.request.FeedRequest;
import edu.byu.cs.tweeter.shared.model.response.FeedResponse;

public class FeedPresenter {

    private final View view;

    public interface View { }

    public FeedPresenter(View view) {
        this.view = view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public FeedResponse getFeed(FeedRequest request) throws IOException, TweeterRemoteException {
        FeedServiceProxy feedServiceProxy = getFeedService();
        return feedServiceProxy.getFeed(request);
    }

    FeedServiceProxy getFeedService() {
        return new FeedServiceProxy();
    }
}
