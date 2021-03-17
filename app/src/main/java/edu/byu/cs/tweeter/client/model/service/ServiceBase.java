package edu.byu.cs.tweeter.client.model.service;

import android.os.Build;

import androidx.annotation.RequiresApi;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;

public class ServiceBase {
    /**
     * Returns an instance of {@link ServerFacade}. Allows mocking of the ServerFacade class for
     * testing purposes. All usages of ServerFacade should get their ServerFacade instance from this
     * method to allow for proper mocking.
     *
     * @return the instance.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    ServerFacade getServerFacade() {
        return new ServerFacade();
    }
}
