package edu.byu.cs.tweeter.model.service;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;
import edu.byu.cs.tweeter.util.ByteArrayUtils;

public class FeedService extends ServiceBase {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public FeedResponse getFeed(FeedRequest request) throws IOException {
        FeedResponse response = getServerFacade().getFeed(request);

        if(response.isSuccess()) {
            loadImages(response);
        }

        return response;
    }

    private void loadImages(FeedResponse response) throws IOException {
        for(Status status : response.getStatuses()) {
            // TODO: only load image once per user.
            byte [] bytes = ByteArrayUtils.bytesFromUrl(status.getUser().getImageUrl());
            status.getUser().setImageBytes(bytes);
        }
    }
}
