package edu.byu.cs.tweeter.client.model.service;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;

import edu.byu.cs.tweeter.shared.model.domain.Status;
import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.shared.model.domain.User;
import edu.byu.cs.tweeter.shared.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.model.request.StoryRequest;
import edu.byu.cs.tweeter.shared.model.response.StoryResponse;
import edu.byu.cs.tweeter.client.util.ByteArrayUtils;
import edu.byu.cs.tweeter.shared.model.service.StoryServiceInterface;

public class StoryServiceProxy extends ServiceBase implements StoryServiceInterface {

    private static final String URL_PATH = "/view/story";

    public StoryServiceProxy(){}

    @RequiresApi(api = Build.VERSION_CODES.O)
    public StoryResponse getStory(StoryRequest request) throws IOException, TweeterRemoteException {
        StoryResponse response = getServerFacade().getStory(request, URL_PATH);

        if(response.isSuccess()) {
            loadImages(response);
        }

        return response;
    }

    private void loadImages(StoryResponse response) throws IOException {
        for(Status status : response.getStatuses()) {
            //TODO: Fix hard coding the user

            byte [] bytes = ByteArrayUtils.bytesFromUrl(status.getUser().getImageUrl());
            status.getUser().setImageBytes(bytes);

        }
    }
}
