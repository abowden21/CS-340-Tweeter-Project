package edu.byu.cs.tweeter.model.service;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.GetUserRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;
import edu.byu.cs.tweeter.model.service.response.GetUserResponse;
import edu.byu.cs.tweeter.util.ByteArrayUtils;

public class GetUserService extends ServiceBase {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public GetUserResponse getUser(GetUserRequest request) throws IOException {
        GetUserResponse response = getServerFacade().getUser(request);

        if(response.getRetrievedUser() != null) {
            loadImages(response);
        }

        return response;
    }

    private void loadImages(GetUserResponse response) throws IOException {
        byte [] bytes = ByteArrayUtils.bytesFromUrl(response.getRetrievedUser().getImageUrl());
        response.getRetrievedUser().setImageBytes(bytes);
    }
}
