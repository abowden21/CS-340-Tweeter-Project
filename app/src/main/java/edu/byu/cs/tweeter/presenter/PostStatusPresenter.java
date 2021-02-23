package edu.byu.cs.tweeter.presenter;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.PostStatusService;
import edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.service.response.PostStatusResponse;
import edu.byu.cs.tweeter.view.main.main.MainActivity;

public class PostStatusPresenter {

        private final Fragment view;
        private final PostStatusService service;

        public interface Fragment {
                public void requestClose();
                public void requestSendTweet(String message);
        }

        public PostStatusPresenter(Fragment view) {
                this.view = view;
                this.service = new PostStatusService();
        }

        public PostStatusService getPostStatusService() {
                return this.service;
        }

        public boolean validatePost(String message) {
                return message.length() > 0 && message.length() <= 140;
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        public PostStatusResponse sendStatus(PostStatusRequest postStatusRequest) throws IOException {
                return getPostStatusService().sendStatus(postStatusRequest);
        }
}
