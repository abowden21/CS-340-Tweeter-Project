package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;
import edu.byu.cs.tweeter.presenter.FeedPresenter;


public class GetFeedTask extends AsyncTask<FeedRequest, Void, FeedResponse> {
    private final FeedPresenter presenter;
    private final GetFeedTask.Observer observer;
    private Exception exception;

    public interface Observer {
        void feedRetrieved(FeedResponse feedResponse);
        void handleException(Exception exception);
    }

    public GetFeedTask(FeedPresenter presenter, GetFeedTask.Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected FeedResponse doInBackground(FeedRequest... feedRequests) {

        FeedResponse response = null;

        try {
            response = presenter.getFeed(feedRequests[0]);
        } catch (IOException ex) {
            exception = ex;
        }

        return response;
    }

    @Override
    protected void onPostExecute(FeedResponse feedResponse) {
        if(exception != null) {
            observer.handleException(exception);
        } else {
            observer.feedRetrieved(feedResponse);
        }
    }
}