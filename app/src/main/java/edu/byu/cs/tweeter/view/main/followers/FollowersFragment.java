package edu.byu.cs.tweeter.view.main.followers;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FollowersRequest;
import edu.byu.cs.tweeter.model.service.response.FollowersResponse;
import edu.byu.cs.tweeter.presenter.FollowersPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.GetFollowersTask;
import edu.byu.cs.tweeter.view.main.FollowRecyclerViewPaginationScrollListener;
import edu.byu.cs.tweeter.view.main.ProfileActivity;
import edu.byu.cs.tweeter.view.main.UserRecyclerViewAdapter;
import edu.byu.cs.tweeter.view.util.ImageUtils;

/**
 * The fragment that displays on the 'Followers' tab.
 */
public class FollowersFragment extends Fragment implements FollowersPresenter.View {

    private static final String LOG_TAG = "FollowersFragment";
    private static final String USER_KEY = "UserKey";
    private static final String AUTH_TOKEN_KEY = "AuthTokenKey";

    private static final int LOADING_DATA_VIEW = 0;
    private static final int ITEM_VIEW = 1;

    private static final int PAGE_SIZE = 10;

    private User loggedInUser;
    private AuthToken authToken;
    private FollowersPresenter presenter;

    private FollowersRecyclerViewAdapter followersRecyclerViewAdapter;

    /**
     * Creates an instance of the fragment and places the user and auth token in an arguments
     * bundle assigned to the fragment.
     *
     * @param user      the logged in user.
     * @param authToken the auth token for this user's session.
     * @return the fragment.
     */
    public static FollowersFragment newInstance(User user, AuthToken authToken) {
        FollowersFragment fragment = new FollowersFragment();

        Bundle args = new Bundle(2);
        args.putSerializable(USER_KEY, user);
        args.putSerializable(AUTH_TOKEN_KEY, authToken);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_followers, container, false);

        //noinspection ConstantConditions
        loggedInUser = (User) getArguments().getSerializable(USER_KEY);
        authToken = (AuthToken) getArguments().getSerializable(AUTH_TOKEN_KEY);

        presenter = new FollowersPresenter(this);

        RecyclerView FollowersRecyclerView = view.findViewById(R.id.followersRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        FollowersRecyclerView.setLayoutManager(layoutManager);

        followersRecyclerViewAdapter = new FollowersRecyclerViewAdapter();
        FollowersRecyclerView.setAdapter(followersRecyclerViewAdapter);

        FollowersRecyclerView.addOnScrollListener(new FollowRecyclerViewPaginationScrollListener(layoutManager, followersRecyclerViewAdapter));

        return view;
    }
    private class FollowerHolder extends RecyclerView.ViewHolder {

        private final ImageView userImage;
        private final TextView userAlias;
        private final TextView userName;
        private User currentUser;

        /**
         * Creates an instance and sets an OnClickListener for the user's row.
         *
         * @param itemView the view on which the user will be displayed.
         */
        FollowerHolder(@NonNull View itemView, int viewType) {
            super(itemView);

            if(viewType == ITEM_VIEW) {
                userImage = itemView.findViewById(R.id.userImage);
                userAlias = itemView.findViewById(R.id.userAlias);
                userName = itemView.findViewById(R.id.userName);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), ProfileActivity.class);
                        intent.putExtra(ProfileActivity.LOGGED_IN_USER_KEY, loggedInUser);
                        intent.putExtra(ProfileActivity.CURRENT_USER_KEY, currentUser);
                        intent.putExtra(ProfileActivity.AUTH_TOKEN_KEY, authToken);

                        startActivity(intent);
                    }
                });
            } else {
                userImage = null;
                userAlias = null;
                userName = null;
            }
        }

        /**
         * Binds the user's data to the view.
         *
         * @param user the user.
         */
        void bindUser(User user) {
            userImage.setImageDrawable(ImageUtils.drawableFromByteArray(user.getImageBytes()));
            userAlias.setText(user.getAlias());
            userName.setText(user.getName());
            currentUser = user;
        }
    }

    private class FollowersRecyclerViewAdapter extends UserRecyclerViewAdapter<FollowerHolder> implements GetFollowersTask.Observer {

        private User lastFollower;

        FollowersRecyclerViewAdapter() {
            super(LOADING_DATA_VIEW, ITEM_VIEW);
        }

        @Override
        protected void loadMoreItems() {
            super.loadMoreItems();
            GetFollowersTask getFollowingTask = new GetFollowersTask(presenter, this);
            FollowersRequest request = new FollowersRequest(loggedInUser.getAlias(), PAGE_SIZE, (lastFollower == null ? null : lastFollower.getAlias()));
            getFollowingTask.execute(request);
        }

        @Override
        public void handleException(Exception exception) {
            super.handleException(exception);
            Log.e(LOG_TAG, exception.getMessage(), exception);
            Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void followersRetrieved(FollowersResponse followingResponse) {
            List<User> followers = followingResponse.getFollowers();

            lastFollower = (followers.size() > 0) ? followers.get(followers.size() -1) : null;
            hasMorePages = followingResponse.getHasMorePages();

            isLoading = false;
            removeLoadingFooter();
            followersRecyclerViewAdapter.addItems(followers);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder followingHolder, int position) {
            if(!isLoading) {
                ((FollowerHolder)followingHolder).bindUser(users.get(position));
            }
        }

        @NonNull
        @Override
        public FollowerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(FollowersFragment.this.getContext());
            View view;

            if(viewType == LOADING_DATA_VIEW) {
                view =layoutInflater.inflate(R.layout.loading_row, parent, false);

            } else {
                view = layoutInflater.inflate(R.layout.user_row, parent, false);
            }

            return new FollowerHolder(view, viewType);
        }
    }

}