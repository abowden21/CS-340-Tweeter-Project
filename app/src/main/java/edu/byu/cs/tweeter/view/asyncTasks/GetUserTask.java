package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.GetUserService;
import edu.byu.cs.tweeter.model.service.request.GetUserRequest;
import edu.byu.cs.tweeter.model.service.response.GetUserResponse;

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

    @Override
    protected GetUserResponse doInBackground(GetUserRequest... getUserRequests) {

        GetUserResponse response = null;

        try {
            GetUserService getUserService = new GetUserService();
            response = getUserService.getUser(getUserRequests[0]);
        } catch (IOException ex) {
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
