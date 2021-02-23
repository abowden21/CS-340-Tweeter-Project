package edu.byu.cs.tweeter.presenter;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.StoryService;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;

/**
 * The presenter for the "story" functionality of the application.
 */
public class StoryPresenter {

    private final View view;

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View { }

    /**
     * Creates an instance.
     *
     * @param view the view for which this class is the presenter.
     */
    public StoryPresenter(View view) {
        this.view = view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public StoryResponse getStory(StoryRequest request) throws IOException {
        StoryService storyService = getStoryService();
        return storyService.getStory(request);
    }

    StoryService getStoryService() {
        return new StoryService();
    }
}
