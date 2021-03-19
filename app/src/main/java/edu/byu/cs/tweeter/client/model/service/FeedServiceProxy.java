package edu.byu.cs.tweeter.client.model.service;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;

import edu.byu.cs.tweeter.client.util.ByteArrayUtils;
import edu.byu.cs.tweeter.shared.model.domain.Status;
import edu.byu.cs.tweeter.shared.model.domain.User;
import edu.byu.cs.tweeter.shared.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.model.request.FeedRequest;
import edu.byu.cs.tweeter.shared.model.response.FeedResponse;
import edu.byu.cs.tweeter.shared.model.service.FeedServiceInterface;

public class FeedServiceProxy extends ServiceBase implements FeedServiceInterface {

    private static final String URL_PATH = "/view/feed";

    public FeedServiceProxy(){}

    @RequiresApi(api = Build.VERSION_CODES.O)
    public FeedResponse getFeed(FeedRequest request) throws IOException, TweeterRemoteException {
        FeedResponse response = getServerFacade().getFeed(request, URL_PATH);

        if(response.isSuccess()) {
            loadImages(response);
        }

        return response;
    }

    private void loadImages(FeedResponse response) throws IOException {
        for(Status status : response.getStatuses()) {
            User user = status.getUser();

            if (user.getImageBytes() == null || user.getImageBytes().length == 0) {
                byte[] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
                user.setImageBytes(bytes);
            }
        }
    }
}
