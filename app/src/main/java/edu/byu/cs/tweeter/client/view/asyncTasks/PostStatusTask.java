package edu.byu.cs.tweeter.client.view.asyncTasks;

import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;

import edu.byu.cs.tweeter.shared.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.model.request.PostStatusRequest;
import edu.byu.cs.tweeter.shared.model.response.PostStatusResponse;
import edu.byu.cs.tweeter.client.presenter.PostStatusPresenter;

public class PostStatusTask extends AsyncTask<PostStatusRequest, Void, PostStatusResponse> {

    private final PostStatusPresenter presenter;
    private final PostStatusTask.Observer observer;
    private Exception exception;

    public interface Observer {
        void postStatusSuccessful(PostStatusResponse postStatusResponse);
        void postStatusUnsuccessful(PostStatusResponse postStatusResponse);
        void handleException(Exception ex);
    }

    public PostStatusTask(PostStatusPresenter presenter, PostStatusTask.Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected PostStatusResponse doInBackground(PostStatusRequest... postStatusRequests) {
        PostStatusResponse postStatusResponse = null;

        try {
            postStatusResponse = presenter.sendStatus(postStatusRequests[0]);
        } catch (IOException | TweeterRemoteException e) {
            observer.handleException(e);
        }

        return postStatusResponse;
    }

    @Override
    protected void onPostExecute(PostStatusResponse postStatusResponse) {
        if(exception != null) {
            observer.handleException(exception);
        } else if(postStatusResponse.isSuccess()) {
            observer.postStatusSuccessful(postStatusResponse);
        } else {
            observer.postStatusUnsuccessful(postStatusResponse);
        }
    }
}

