package edu.byu.cs.tweeter.client.view.asyncTasks;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.IOException;

import edu.byu.cs.tweeter.client.util.ByteArrayUtils;
import edu.byu.cs.tweeter.shared.model.domain.User;
import edu.byu.cs.tweeter.shared.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.model.request.RegisterRequest;
import edu.byu.cs.tweeter.shared.model.response.RegisterResponse;
import edu.byu.cs.tweeter.client.presenter.LoginPresenter;

public class RegisterTask extends AsyncTask<RegisterRequest, Void, RegisterResponse> {

    private final LoginPresenter presenter;
    private final Observer observer;
    private Exception exception;

    public interface Observer {
        void registerSuccessful(RegisterResponse registerResponse);
        void registerUnsuccessful(RegisterResponse registerResponse);
        void handleException(Exception ex);
    }

    public RegisterTask(LoginPresenter presenter, Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected RegisterResponse doInBackground(RegisterRequest... registerRequests) {
        RegisterResponse registerResponse = null;

        try {
            registerResponse = presenter.register(registerRequests[0]);

            if(registerResponse.isSuccess()) {
                loadImage(registerResponse.getUser());
            }
        } catch (IOException | TweeterRemoteException ex) {
            exception = ex;
        }

        return registerResponse;
    }

    private void loadImage(User user) {
        try {
            byte [] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
            user.setImageBytes(bytes);
        } catch (IOException e) {
            Log.e(this.getClass().getName(), e.toString(), e);
        }
    }

    @Override
    protected void onPostExecute(RegisterResponse registerResponse) {
        if(exception != null) {
            observer.handleException(exception);
        } else if(registerResponse.isSuccess()) {
            observer.registerSuccessful(registerResponse);
        } else {
            observer.registerUnsuccessful(registerResponse);
        }
    }
}
