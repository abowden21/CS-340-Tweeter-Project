package edu.byu.cs.tweeter.view.main.followers;

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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.presenter.FollowersPresenter;
//import edu.byu.cs.tweeter.model.service.request.FollowersRequest;
//import edu.byu.cs.tweeter.model.service.response.FollowersResponse;
//import edu.byu.cs.tweeter.presenter.FollowersPresenter;
//import edu.byu.cs.tweeter.view.asyncTasks.GetFollowersTask;
//import edu.byu.cs.tweeter.view.util.ImageUtils;

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

//    private User user;
//    private AuthToken authToken;
    private FollowersPresenter presenter;

//    private FollowersRecyclerViewAdapter FollowersRecyclerViewAdapter;

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
//        user = (User) getArguments().getSerializable(USER_KEY);
//        authToken = (AuthToken) getArguments().getSerializable(AUTH_TOKEN_KEY);

        presenter = new FollowersPresenter(this);

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