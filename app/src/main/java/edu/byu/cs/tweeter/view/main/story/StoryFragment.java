package edu.byu.cs.tweeter.view.main.story;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.presenter.StoryPresenter;

/**
 * The fragment that displays on the 'Followers' tab.
 */
public class StoryFragment extends Fragment implements StoryPresenter.View {

    private static final String LOG_TAG = "FollowersFragment";
    private static final String USER_KEY = "UserKey";
    private static final String AUTH_TOKEN_KEY = "AuthTokenKey";

    private static final int LOADING_DATA_VIEW = 0;
    private static final int ITEM_VIEW = 1;

    private static final int PAGE_SIZE = 10;

    //    private User user;
//    private AuthToken authToken;
    private StoryPresenter presenter;

//    private FollowersRecyclerViewAdapter FollowersRecyclerViewAdapter;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_story, container, false);

        //noinspection ConstantConditions
//        user = (User) getArguments().getSerializable(USER_KEY);
//        authToken = (AuthToken) getArguments().getSerializable(AUTH_TOKEN_KEY);

        presenter = new StoryPresenter(this);

//        RecyclerView FollowersRecyclerView = view.findViewById(R.id.followersRecyclerView);
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
//        FollowersRecyclerView.setLayoutManager(layoutManager);

//        FollowersRecyclerViewAdapter = new FollowersRecyclerViewAdapter();
//        FollowersRecyclerView.setAdapter(FollowersRecyclerViewAdapter);
//
//        FollowersRecyclerView.addOnScrollListener(new FollowRecyclerViewPaginationScrollListener(layoutManager));

        return view;
    }

    // TODO copy/paste the rest
}