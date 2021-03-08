package edu.byu.cs.tweeter.client.model.service;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.shared.model.request.PostStatusRequest;
import edu.byu.cs.tweeter.shared.model.response.PostStatusResponse;

public class PostStatusService extends ServiceBase {

    public PostStatusService(){}

    @RequiresApi(api = Build.VERSION_CODES.O)
    public PostStatusResponse sendStatus(PostStatusRequest postStatusRequest) throws IOException {
        ServerFacade serverFacade = getServerFacade();
        PostStatusResponse postStatusResponse = serverFacade.sendStatus(postStatusRequest);
        return postStatusResponse;
    }

}
