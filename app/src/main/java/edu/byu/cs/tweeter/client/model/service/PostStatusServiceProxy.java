package edu.byu.cs.tweeter.client.model.service;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.shared.model.request.PostStatusRequest;
import edu.byu.cs.tweeter.shared.model.response.PostStatusResponse;
import edu.byu.cs.tweeter.shared.model.service.PostStatusServiceInterface;

public class PostStatusServiceProxy extends ServiceBase implements PostStatusServiceInterface {

    public PostStatusServiceProxy(){}

    @RequiresApi(api = Build.VERSION_CODES.O)
    public PostStatusResponse sendStatus(PostStatusRequest postStatusRequest) throws IOException {
        ServerFacade serverFacade = getServerFacade();
        PostStatusResponse postStatusResponse = serverFacade.sendStatus(postStatusRequest);
        return postStatusResponse;
    }

}
