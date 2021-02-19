package edu.byu.cs.tweeter.view.main.feed;

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
import edu.byu.cs.tweeter.model.domain.Feed;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;
import edu.byu.cs.tweeter.presenter.FeedPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.GetFeedTask;
import edu.byu.cs.tweeter.view.main.StatusRecyclerViewAdapter;
import edu.byu.cs.tweeter.view.main.StatusRecyclerViewPaginationScrollListener;
import edu.byu.cs.tweeter.view.util.ImageUtils;

/**
 * The fragment that displays on the 'Feed' tab.
 */
public class FeedFragment extends Fragment implements FeedPresenter.View {

    private static final String LOG_TAG = "FeedFragment";
    private static final String USER_KEY = "UserKey";
    private static final String AUTH_TOKEN_KEY = "AuthTokenKey";

    private static final int LOADING_DATA_VIEW = 0;
    private static final int ITEM_VIEW = 1;

    private static final int PAGE_SIZE = 10;

    private User user;
    private AuthToken authToken;
    private FeedPresenter presenter;

    private FeedRecyclerViewAdapter feedRecyclerViewAdapter;

    /**
     * Creates an instance of the fragment and places the user and auth token in an arguments
     * bundle assigned to the fragment.
     *
     * @param user      the logged in user.
     * @param authToken the auth token for this user's session.
     * @return the fragment.
     */
    public static FeedFragment newInstance(User user, AuthToken authToken) {
        FeedFragment fragment = new FeedFragment();

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
        View view = inflater.inflate(R.layout.fragment_feed, container, false);

        //noinspection ConstantConditions
        user = (User) getArguments().getSerializable(USER_KEY);
        authToken = (AuthToken) getArguments().getSerializable(AUTH_TOKEN_KEY);

        presenter = new FeedPresenter(this);

        RecyclerView feedRecyclerView = view.findViewById(R.id.feedRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        feedRecyclerView.setLayoutManager(layoutManager);

        feedRecyclerViewAdapter = new FeedRecyclerViewAdapter();
        feedRecyclerView.setAdapter(feedRecyclerViewAdapter);

        feedRecyclerView.addOnScrollListener(new StatusRecyclerViewPaginationScrollListener(layoutManager, feedRecyclerViewAdapter));

        return view;
    }

    private class FeedHolder extends RecyclerView.ViewHolder {

        private final ImageView userImage;
        private final TextView userAlias;
        private final TextView userName;
        private final TextView statusBody;
        private final TextView timestamp;

        FeedHolder(@NonNull View itemView, int viewType) {
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
            statusBody.setText(feedRecyclerViewAdapter.makeSpannableString(status));
            //statusBody.setText(status.getMessage());
            timestamp.setText(status.getTimeStamp().toString());
        }
    }

    private class FeedRecyclerViewAdapter extends StatusRecyclerViewAdapter<FeedHolder> implements GetFeedTask.Observer {

        private Status lastStatus;

        @RequiresApi(api = Build.VERSION_CODES.O)
        FeedRecyclerViewAdapter() {
            super(LOADING_DATA_VIEW, ITEM_VIEW);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void loadMoreItems() {
            super.loadMoreItems();

            GetFeedTask getFeedTask = new GetFeedTask(presenter, this);
            FeedRequest request = new FeedRequest(user.getAlias(), PAGE_SIZE, (lastStatus == null ? null : lastStatus.getTimeStamp()));
            getFeedTask.execute(request);
        }

        @Override
        public void mentionedClicked(String alias) {
            Toast.makeText(getContext(), alias, Toast.LENGTH_LONG).show();
        }

        @Override
        public void handleException(Exception exception) {
            super.handleException(exception);
            Log.e(LOG_TAG, exception.getMessage(), exception);
            Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void feedRetrieved(FeedResponse feedResponse) {
            List<Status> statuses = feedResponse.getStatuses();

            lastStatus = (statuses.size() > 0) ? statuses.get(statuses.size() -1) : null;
            hasMorePages = feedResponse.getHasMorePages();

            isLoading = false;
            removeLoadingFooter();
            feedRecyclerViewAdapter.addItems(statuses);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder feedHolder, int position) {
            if(!isLoading) {
                ((FeedFragment.FeedHolder)feedHolder).bindUser(statuses.get(position));
            }
        }

        @NonNull
        @Override
        public FeedFragment.FeedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(FeedFragment.this.getContext());
            View view;

            if(viewType == LOADING_DATA_VIEW) {
                view =layoutInflater.inflate(R.layout.loading_row, parent, false);

            } else {
                view = layoutInflater.inflate(R.layout.status_row, parent, false);
            }

            return new FeedFragment.FeedHolder(view, viewType);
        }
    }
}