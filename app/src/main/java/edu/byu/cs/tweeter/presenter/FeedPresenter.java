package edu.byu.cs.tweeter.presenter;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.FeedService;
import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;

public class FeedPresenter {

    private final View view;

    public interface View { }

    public FeedPresenter(View view) {
        this.view = view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public FeedResponse getFeed(FeedRequest request) throws IOException {
        FeedService feedService = getFeedService();
        return feedService.getFeed(request);
    }

    FeedService getFeedService() {
        return new FeedService();
    }
}
