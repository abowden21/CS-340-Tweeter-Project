package edu.byu.cs.tweeter.client.model.service;

import android.os.Build;

import androidx.annotation.RequiresApi;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;

public class ServiceBase {
    private ServerFacade serverFacade;
    @RequiresApi(api = Build.VERSION_CODES.O)
    ServerFacade getServerFacade() {
        if (serverFacade == null)
            serverFacade = new ServerFacade();
        return serverFacade;
    }
}
