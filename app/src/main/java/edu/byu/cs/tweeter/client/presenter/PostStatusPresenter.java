package edu.byu.cs.tweeter.client.presenter;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.PostStatusServiceProxy;
import edu.byu.cs.tweeter.shared.model.request.PostStatusRequest;
import edu.byu.cs.tweeter.shared.model.response.PostStatusResponse;

public class PostStatusPresenter {

        private final Fragment view;
        private final PostStatusServiceProxy service;

        public interface Fragment {
                public void requestClose();
                public void requestSendTweet(String message);
        }

        public PostStatusPresenter(Fragment view) {
                this.view = view;
                this.service = new PostStatusServiceProxy();
        }

        public PostStatusServiceProxy getPostStatusService() {
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
