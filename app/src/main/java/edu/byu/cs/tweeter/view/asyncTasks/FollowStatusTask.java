package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.request.FollowStatusRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;
import edu.byu.cs.tweeter.model.service.response.FollowStatusResponse;
import edu.byu.cs.tweeter.presenter.FollowPresenter;

public class FollowStatusTask extends AsyncTask<FollowStatusRequest, Void, FollowStatusResponse> {

    private final FollowPresenter presenter;
    private final Observer observer;
    private Exception exception;

    /**
     * An observer interface to be implemented by observers who want to be notified when this task
     * completes.
     */
    public interface Observer {
        void followStatusSuccessful(FollowStatusResponse followStatusResponse);
        void followStatusUnsuccessful(FollowStatusResponse followStatusResponse );
        void handleException(Exception ex);
    }

    public FollowStatusTask(FollowPresenter presenter, Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected FollowStatusResponse doInBackground(FollowStatusRequest... followStatusRequests) {
        FollowStatusResponse followStatusResponse = null;

        try {
            followStatusResponse = presenter.getFollowStatus(followStatusRequests[0]);

        } catch (IOException ex) {
            exception = ex;
        }

        return followStatusResponse;
    }

    @Override
    protected void onPostExecute(FollowStatusResponse followStatusResponse) {
        if(exception != null) {
            observer.handleException(exception);
        } else if(followStatusResponse.isSuccess() ) {
            observer.followStatusSuccessful(followStatusResponse);
        } else if(!followStatusResponse.isSuccess() ) {
            observer.followStatusUnsuccessful(followStatusResponse);
        }

    }
}

