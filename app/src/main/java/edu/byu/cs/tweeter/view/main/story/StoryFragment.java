package edu.byu.cs.tweeter.view.main.story;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.GetUserRequest;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.GetUserResponse;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;
import edu.byu.cs.tweeter.presenter.StoryPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.GetStoryTask;
import edu.byu.cs.tweeter.view.asyncTasks.GetUserTask;
import edu.byu.cs.tweeter.view.main.profile.ProfileActivity;
import edu.byu.cs.tweeter.view.main.recycler.StatusRecyclerViewAdapter;
import edu.byu.cs.tweeter.view.main.recycler.StatusRecyclerViewPaginationScrollListener;
import edu.byu.cs.tweeter.view.util.ImageUtils;

/**
 * The fragment that displays on the 'Story' tab.
 */
public class StoryFragment extends Fragment implements StoryPresenter.View {

    private static final String LOG_TAG = "StoriesFragment";
    private static final String USER_KEY = "UserKey";
    private static final String AUTH_TOKEN_KEY = "AuthTokenKey";

    private static final int LOADING_DATA_VIEW = 0;
    private static final int ITEM_VIEW = 1;

    private static final int PAGE_SIZE = 10;

    private User user;
    private AuthToken authToken;
    private StoryPresenter presenter;

    private StoryRecyclerViewAdapter storyRecyclerViewAdapter;

    /**
     * Creates an instance of the fragment and places the user and auth token in an arguments
     * bundle assigned to the fragment.
     *
     * @param user      the logged in user.
     * @param authToken the auth token for this user's session.
     * @return the fragment.
     */
    public static StoryFragment newInstance(User user, AuthToken authToken) {
        StoryFragment fragment = new StoryFragment();

        Bundle args = new Bundle(2);
        args.putSerializable(USER_KEY, user);
        args.putSerializable(AUTH_TOKEN_KEY, authToken);

        fragment.setArguments(args);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_story, container, false);

        //noinspection ConstantConditions
        user = (User) getArguments().getSerializable(USER_KEY);
        authToken = (AuthToken) getArguments().getSerializable(AUTH_TOKEN_KEY);

        presenter = new StoryPresenter(this);

        RecyclerView storyRecyclerView = view.findViewById(R.id.storyRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        storyRecyclerView.setLayoutManager(layoutManager);

        storyRecyclerViewAdapter = new StoryRecyclerViewAdapter();
        storyRecyclerView.setAdapter(storyRecyclerViewAdapter);

        storyRecyclerView.addOnScrollListener(new StatusRecyclerViewPaginationScrollListener(layoutManager, storyRecyclerViewAdapter));

        return view;
    }

    private class StoryHolder extends RecyclerView.ViewHolder {

        private final ImageView userImage;
        private final TextView userAlias;
        private final TextView userName;
        private final TextView statusBody;
        private final TextView timestamp;

        StoryHolder(@NonNull View itemView, int viewType) {
            super(itemView);

            if(viewType == ITEM_VIEW) {
                userImage = itemView.findViewById(R.id.userImage);
                userAlias = itemView.findViewById(R.id.userAlias);
                userName = itemView.findViewById(R.id.userName);
                statusBody = itemView.findViewById(R.id.statusBody);
                timestamp = itemView.findViewById(R.id.timestamp);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(), "[Follower] You selected '" + userName.getText() + "'.", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                userImage = null;
                userAlias = null;
                userName = null;
                statusBody = null;
                timestamp = null;
            }
        }

        void bindUser(Status status) {
            userImage.setImageDrawable(ImageUtils.drawableFromByteArray(status.getUser().getImageBytes()));
            userAlias.setText(status.getUser().getAlias());
            userName.setText(status.getUser().getName());
            statusBody.setText(storyRecyclerViewAdapter.makeSpannableString(status));
            //statusBody.setText(status.getMessage());
            timestamp.setText(status.getTimeStamp().toString());
        }
    }

    private class StoryRecyclerViewAdapter extends StatusRecyclerViewAdapter<StoryHolder> implements GetStoryTask.Observer, GetUserTask.Observer {

        private Status lastStatus;

        @RequiresApi(api = Build.VERSION_CODES.O)
        StoryRecyclerViewAdapter() {
            super(LOADING_DATA_VIEW, ITEM_VIEW);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void loadMoreItems() {
            super.loadMoreItems();

            GetStoryTask getStoryTask = new GetStoryTask(presenter, this);
            StoryRequest request = new StoryRequest(user.getAlias(), PAGE_SIZE, (lastStatus == null ? null : lastStatus.getTimeStamp()));
            getStoryTask.execute(request);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void mentionedClicked(String alias) {
            GetUserTask getUserTask = new GetUserTask(this);
            GetUserRequest getUserRequest = new GetUserRequest(alias);
            getUserTask.execute(getUserRequest);
        }

        @Override
        public void userRetrieved(GetUserResponse getUserResponse) {
            User retrievedUser = getUserResponse.getRetrievedUser();
            if (retrievedUser != null) {
                Intent intent = new Intent(getContext(), ProfileActivity.class);

                intent.putExtra(ProfileActivity.LOGGED_IN_USER_KEY, user);
                intent.putExtra(ProfileActivity.CURRENT_USER_KEY, retrievedUser);
                intent.putExtra(ProfileActivity.AUTH_TOKEN_KEY, authToken);

                startActivity(intent);
            }
            else {
                Toast.makeText(getContext(), "Non-existing User", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void handleException(Exception exception) {
            super.handleException(exception);
            Log.e(LOG_TAG, exception.getMessage(), exception);
            Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void storyRetrieved(StoryResponse storyResponse) {
            List<Status> statuses = storyResponse.getStatuses();

            lastStatus = (statuses.size() > 0) ? statuses.get(statuses.size() -1) : null;
            hasMorePages = storyResponse.getHasMorePages();

            isLoading = false;
            removeLoadingFooter();
            storyRecyclerViewAdapter.addItems(statuses);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder storyHolder, int position) {
            if(!isLoading) {
                ((StoryFragment.StoryHolder)storyHolder).bindUser(statuses.get(position));
            }
        }

        @NonNull
        @Override
        public StoryFragment.StoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(StoryFragment.this.getContext());
            View view;

            if(viewType == LOADING_DATA_VIEW) {
                view =layoutInflater.inflate(R.layout.loading_row, parent, false);

            } else {
                view = layoutInflater.inflate(R.layout.status_row, parent, false);
            }

            return new StoryFragment.StoryHolder(view, viewType);
        }
    }


}