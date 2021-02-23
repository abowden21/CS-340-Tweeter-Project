package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import edu.byu.cs.tweeter.model.service.request.UserFollowCountRequest;
import edu.byu.cs.tweeter.model.service.response.UserFollowCountResponse;
import edu.byu.cs.tweeter.presenter.GetFollowCountPresenter;

public class GetUserFollowCountTask extends AsyncTask<UserFollowCountRequest, Void, UserFollowCountResponse> {

    private final GetFollowCountPresenter presenter;
    private final GetUserFollowCountTask.Observer observer;
    private Exception exception;

    public interface Observer {
        void userFollowCountRetrieved(UserFollowCountResponse followCountResponse);
        void userFollowCountUnsuccessful(UserFollowCountResponse followCountResponse);
        void handleException(Exception ex);
    }

    public GetUserFollowCountTask(GetFollowCountPresenter presenter, GetUserFollowCountTask.Observer observer) {
        if (observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected UserFollowCountResponse doInBackground(UserFollowCountRequest... followCountRequests) {

        return presenter.getUserFollowCount(followCountRequests[0]);
    }

    @Override
    protected void onPostExecute(UserFollowCountResponse followCountResponse) {
        if(exception != null) {
            observer.handleException(exception);
        } else if(followCountResponse.isSuccess()) {
            observer.userFollowCountRetrieved(followCountResponse);
        } else {
            observer.userFollowCountUnsuccessful(followCountResponse);
        }
    }

}
