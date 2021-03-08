package edu.byu.cs.tweeter.client.model.service;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;

import edu.byu.cs.tweeter.shared.model.request.GetUserRequest;
import edu.byu.cs.tweeter.shared.model.response.GetUserResponse;
import edu.byu.cs.tweeter.client.util.ByteArrayUtils;

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
