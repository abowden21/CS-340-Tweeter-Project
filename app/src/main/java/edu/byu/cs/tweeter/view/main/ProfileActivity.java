package edu.byu.cs.tweeter.view.main;

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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;
import edu.byu.cs.tweeter.presenter.FollowPresenter;
import edu.byu.cs.tweeter.presenter.StoryPresenter;
import edu.byu.cs.tweeter.view.LoginActivity;
import edu.byu.cs.tweeter.view.asyncTasks.FollowTask;
import edu.byu.cs.tweeter.view.asyncTasks.LoginTask;
import edu.byu.cs.tweeter.view.util.ImageUtils;

public class ProfileActivity  extends AppCompatActivity implements FollowTask.Observer {
    private static final String LOG_TAG = "LoginActivity";
    public static final String LOGGED_IN_USER_KEY = "LoggedInUser";
    public static final String CURRENT_USER_KEY = "CurrentUser";
    public static final String AUTH_TOKEN_KEY = "AuthTokenKey";
    Button followButton;
    private FollowPresenter presenter;

    //TODO: back end needs to check status
    private boolean isFollowingUser = false;

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

        //TODO: call if isFollowingUser is set
        //reuse follow presenter and follow service
        //make request and response using a boolean and a async task
        //profile activity implements the new task.observer

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

        TextView followeeCount = findViewById(R.id.followeeCount);
        followeeCount.setText(getString(R.string.followeeCount, 42));

        TextView followerCount = findViewById(R.id.followerCount);
        followerCount.setText(getString(R.string.followerCount, 27));

        followButton = findViewById(R.id.followButton);
        followButton.setBackgroundColor(Color.GRAY);

        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                FollowRequest followRequest = new FollowRequest(!isFollowingUser, loggedInUser.getAlias(), currentUser.getAlias());
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
    public void handleException(Exception ex) {
        Log.e(LOG_TAG, ex.getMessage(), ex);
        Toast.makeText(this, "Failed to follow/unfollow because of exception: " + ex.getMessage(), Toast.LENGTH_LONG).show();
    }
}
