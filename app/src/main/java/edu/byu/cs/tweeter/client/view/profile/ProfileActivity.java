package edu.byu.cs.tweeter.client.view.profile;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import edu.byu.cs.tweeter.client.presenter.GetFollowCountPresenter;
import edu.byu.cs.tweeter.client.view.asyncTasks.FollowStatusTask;
import edu.byu.cs.tweeter.client.view.asyncTasks.GetUserFollowCountTask;
import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.shared.model.domain.AuthToken;
import edu.byu.cs.tweeter.shared.model.domain.User;
import edu.byu.cs.tweeter.shared.model.request.FollowRequest;
import edu.byu.cs.tweeter.shared.model.request.FollowStatusRequest;
import edu.byu.cs.tweeter.shared.model.request.UserFollowCountRequest;
import edu.byu.cs.tweeter.shared.model.response.FollowResponse;
import edu.byu.cs.tweeter.shared.model.response.FollowStatusResponse;
import edu.byu.cs.tweeter.shared.model.response.UserFollowCountResponse;
import edu.byu.cs.tweeter.client.presenter.FollowPresenter;
import edu.byu.cs.tweeter.client.view.asyncTasks.FollowTask;
import edu.byu.cs.tweeter.client.view.util.ImageUtils;

public class ProfileActivity  extends AppCompatActivity implements FollowTask.Observer, FollowStatusTask.Observer, GetUserFollowCountTask.Observer {
    private static final String LOG_TAG = "LoginActivity";
    public static final String LOGGED_IN_USER_KEY = "LoggedInUser";
    public static final String CURRENT_USER_KEY = "CurrentUser";
    public static final String AUTH_TOKEN_KEY = "AuthTokenKey";
    Button followButton;
    private FollowPresenter presenter;
    private GetFollowCountPresenter fcPresenter;
    TextView followeeCount;
    TextView followerCount;
    private boolean isFollowingUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        User loggedInUser = (User) getIntent().getSerializableExtra(LOGGED_IN_USER_KEY);
        User currentUser = (User) getIntent().getSerializableExtra(CURRENT_USER_KEY);
        if(currentUser == null) {
            throw new RuntimeException("User not passed to activity");
        }

        AuthToken authToken = (AuthToken) getIntent().getSerializableExtra(AUTH_TOKEN_KEY);
        presenter = new FollowPresenter();
        fcPresenter = new GetFollowCountPresenter();

        FollowStatusTask followStatusTask = new FollowStatusTask(presenter, ProfileActivity.this);
        FollowStatusRequest followStatusRequest = new FollowStatusRequest(loggedInUser.getAlias(), currentUser.getAlias());
        followStatusTask.execute(followStatusRequest);

        ProfilePagerAdapter profilePagerAdapter = new ProfilePagerAdapter(this, getSupportFragmentManager(), currentUser, authToken);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(profilePagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        TextView userName = findViewById(R.id.userName);
        userName.setText(currentUser.getName());

        TextView userAlias = findViewById(R.id.userAlias);
        userAlias.setText(currentUser.getAlias());

        ImageView userImageView = findViewById(R.id.userImage);
        userImageView.setImageDrawable(ImageUtils.drawableFromByteArray(currentUser.getImageBytes()));

        followeeCount = findViewById(R.id.followeeCount);
        followerCount = findViewById(R.id.followerCount);

        GetUserFollowCountTask userFollowCountTask = new GetUserFollowCountTask(fcPresenter, this);
        UserFollowCountRequest userFollowCountRequest = new UserFollowCountRequest(currentUser.getAlias());
        userFollowCountTask.execute(userFollowCountRequest);

        followButton = findViewById(R.id.followButton);

        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                FollowRequest followRequest = new FollowRequest(authToken, !isFollowingUser, loggedInUser.getAlias(), currentUser.getAlias());
                FollowTask followTask = new FollowTask(presenter,  ProfileActivity.this);
                followTask.execute(followRequest);
            }
        });
    }

    @Override
    public void followSuccessful(FollowResponse followResponse) {
        followButton.setText("Following");
        followButton.setBackgroundColor(Color.RED);
        isFollowingUser = true;
    }

    @Override
    public void unfollowSuccessful(FollowResponse followResponse) {
        followButton.setText("Follow");
        followButton.setBackgroundColor(Color.GRAY);
        isFollowingUser = false;
    }

    @Override
    public void actionUnsuccessful(FollowResponse followResponse) {
        Toast.makeText(this, "Failed to follow/unfollow", Toast.LENGTH_LONG).show();
    }

    @Override
    public void followStatusSuccessful(FollowStatusResponse followStatusResponse) {
        isFollowingUser = followStatusResponse.isFollowingUser();
        if (isFollowingUser) {
            followButton.setText("Following");
            followButton.setBackgroundColor(Color.RED);
        } else {
            followButton.setText("Follow");
            followButton.setBackgroundColor(Color.GRAY);
        }
    }

    @Override
    public void followStatusUnsuccessful(FollowStatusResponse followStatusResponse) {
        Toast.makeText(this, "Failed to retrieve follow status", Toast.LENGTH_LONG).show();
    }

    @Override
    public void handleException(Exception ex) {
        Log.e(LOG_TAG, ex.getMessage(), ex);
        Toast.makeText(this, "Failed to follow/unfollow because of exception: " + ex.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void userFollowCountRetrieved(UserFollowCountResponse followCountResponse) {
        followerCount.setText(getString(R.string.followeeCount, followCountResponse.getFollowers()));
        followeeCount.setText(getString(R.string.followeeCount, followCountResponse.getFollowees()));
    }

    @Override
    public void userFollowCountUnsuccessful(UserFollowCountResponse followCountResponse) {
        followerCount.setText("");
        followeeCount.setText("");
    }
}
