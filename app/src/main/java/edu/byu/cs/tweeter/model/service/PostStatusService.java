package edu.byu.cs.tweeter.model.service;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.service.response.PostStatusResponse;

public class PostStatusService extends ServiceBase {

    public PostStatusService(){}

    @RequiresApi(api = Build.VERSION_CODES.O)
    public PostStatusResponse sendStatus(PostStatusRequest postStatusRequest) throws IOException {
        ServerFacade serverFacade = getServerFacade();
        PostStatusResponse postStatusResponse = serverFacade.sendStatus(postStatusRequest);
        return postStatusResponse;
    }

}
