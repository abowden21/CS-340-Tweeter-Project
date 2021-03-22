package edu.byu.cs.tweeter.client.view.asyncTasks;

import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.GetUserServiceProxy;
import edu.byu.cs.tweeter.shared.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.model.request.GetUserRequest;
import edu.byu.cs.tweeter.shared.model.response.GetUserResponse;

public class GetUserTask extends AsyncTask<GetUserRequest, Void, GetUserResponse> {

    private final Observer observer;
    private Exception exception;

    /**
     * An observer interface to be implemented by observers who want to be notified when this task
     * completes.
     */
    public interface Observer {
        void userRetrieved(GetUserResponse getUserResponse);
        void handleException(Exception exception);
    }

    public GetUserTask(Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }

        this.observer = observer;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected GetUserResponse doInBackground(GetUserRequest... getUserRequests) {

        GetUserResponse response = null;

        try {
            GetUserServiceProxy getUserService = new GetUserServiceProxy();
            response = getUserService.getUser(getUserRequests[0]);
        } catch (IOException | TweeterRemoteException ex) {
            exception = ex;
        }

        return response;
    }

    @Override
    protected void onPostExecute(GetUserResponse getUserResponse) {
        if(exception != null) {
            observer.handleException(exception);
        } else {
            observer.userRetrieved(getUserResponse);
        }
    }
}
