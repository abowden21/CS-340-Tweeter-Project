package edu.byu.cs.tweeter.presenter;

import android.os.Build;

import androidx.annotation.RequiresApi;

import edu.byu.cs.tweeter.model.service.PostStatusService;
import edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.service.response.PostStatusResponse;
import edu.byu.cs.tweeter.view.main.main.MainActivity;

public class PostStatusPresenter {

        private final MainActivity view;

        public interface Fragment {
                public void requestClose();
                public void requestSendTweet(String message);
        }

        public PostStatusPresenter(MainActivity view) {
            this.view = view;
        }

        public boolean validatePost(String message) {
                return message.length() > 0 && message.length() <= 140;
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        public PostStatusResponse sendStatus(PostStatusRequest postStatusRequest) {
                PostStatusService postStatusService = new PostStatusService();
                return postStatusService.sendStatus(postStatusRequest);
        }
}
