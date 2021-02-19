package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.service.response.PostStatusResponse;
import edu.byu.cs.tweeter.presenter.PostStatusPresenter;

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

        postStatusResponse = presenter.sendStatus(postStatusRequests[0]);

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

