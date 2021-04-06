package edu.byu.cs.tweeter.client.view.main;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import edu.byu.cs.tweeter.client.presenter.GetFollowCountPresenter;
import edu.byu.cs.tweeter.client.presenter.LoginPresenter;
import edu.byu.cs.tweeter.client.presenter.PostStatusPresenter;
import edu.byu.cs.tweeter.client.view.asyncTasks.GetUserFollowCountTask;
import edu.byu.cs.tweeter.client.view.asyncTasks.LogoutTask;
import edu.byu.cs.tweeter.client.view.asyncTasks.PostStatusTask;
import edu.byu.cs.tweeter.client.view.util.ImageUtils;
import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.shared.model.domain.AuthToken;
import edu.byu.cs.tweeter.shared.model.domain.User;
import edu.byu.cs.tweeter.shared.model.request.LogoutRequest;
import edu.byu.cs.tweeter.shared.model.request.PostStatusRequest;
import edu.byu.cs.tweeter.shared.model.request.UserFollowCountRequest;
import edu.byu.cs.tweeter.shared.model.response.LogoutResponse;
import edu.byu.cs.tweeter.shared.model.response.PostStatusResponse;
import edu.byu.cs.tweeter.shared.model.response.UserFollowCountResponse;

/**
 * The main activity for the application. Contains tabs for feed, story, following, and followers.
 */
public class
MainActivity extends AppCompatActivity implements LoginPresenter.View, LogoutTask.Observer,
        PostStatusPresenter.Fragment, PostStatusTask.Observer, GetFollowCountPresenter.View, GetUserFollowCountTask.Observer {
    private static final String LOG_TAG = "MainActivity";
    public static final String CURRENT_USER_KEY = "CurrentUser";
    public static final String AUTH_TOKEN_KEY = "AuthTokenKey";

    private AuthToken authToken;
    private User user;

    private LoginPresenter loginPresenter;
    private GetFollowCountPresenter fcPresenter;
    private Toast currentToast;

    FragmentManager fragmentManager;
    FrameLayout postStatusFrame;
    FloatingActionButton postStatusButton;
    PostStatusFragment postFragment;
    PostStatusPresenter postPresenter;
    TextView followeeCount;
    TextView followerCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Same presenter as LoginActivity.
        loginPresenter = new LoginPresenter(this);
        fcPresenter = new GetFollowCountPresenter(this);

        user = (User) getIntent().getSerializableExtra(CURRENT_USER_KEY);
        if(user == null) {
            throw new RuntimeException("User not passed to activity");
        }

        authToken = (AuthToken) getIntent().getSerializableExtra(AUTH_TOKEN_KEY);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), user, authToken);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        postStatusButton = findViewById(R.id.postStatusButton);
        postStatusFrame = findViewById(R.id.postStatusFrame);
        fragmentManager = getSupportFragmentManager();
        postPresenter = new PostStatusPresenter(this);


        // We should use a Java 8 lambda function for the listener (and all other listeners), but
        // they would be unfamiliar to many students who use this code.
        postStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create PostFragment every time to ensure a fresh text box
                // There are other ways to do this, but it's very simple this way.
                postFragment = PostStatusFragment.newInstance(MainActivity.this, user, authToken, postPresenter);
                showPostFragment();
            }
        });

        TextView userName = findViewById(R.id.userName);
        userName.setText(user.getName());

        TextView userAlias = findViewById(R.id.userAlias);
        userAlias.setText(user.getAlias());

        ImageView userImageView = findViewById(R.id.userImage);
        userImageView.setImageDrawable(ImageUtils.drawableFromByteArray(user.getImageBytes()));

        followeeCount = findViewById(R.id.followeeCount);
        followerCount = findViewById(R.id.followerCount);

        GetUserFollowCountTask userFollowCountTask = new GetUserFollowCountTask(fcPresenter, this);
        UserFollowCountRequest userFollowCountRequest = new UserFollowCountRequest(user.getAlias());
        userFollowCountTask.execute(userFollowCountRequest);
    }

    private void showPostFragment() {
        fragmentManager.beginTransaction().add(postStatusFrame.getId(), postFragment).commit();
        postStatusButton.hide();
    }

    private void hidePostFragment() {
        fragmentManager.beginTransaction().remove(postFragment).commit();
        postStatusButton.show();
    }

    // From tweet post fragment
    @Override
    public void requestClose() {
        hidePostFragment();
    }

    @Override
    public void requestSendTweet(String message) {
        PostStatusTask postStatusTask = new PostStatusTask(this.postPresenter, this);
        PostStatusRequest postStatusRequest = new PostStatusRequest(this.authToken.getToken(), message);
        postStatusTask.execute(postStatusRequest);
        sendToast("Sending post...");
    }

    @Override
    public void postStatusSuccessful(PostStatusResponse postStatusResponse) {
        hidePostFragment();
        sendToast("Post successfully sent!");
    }

    @Override
    public void postStatusUnsuccessful(PostStatusResponse postStatusResponse) {
        sendToast(postStatusResponse.getMessage());
    }

    private void sendToast(String message) {
        if (currentToast != null) {
            currentToast.cancel();
        }
        currentToast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        currentToast.show();
    }

    private void navigateAndClearStack() {
        // As it turns out, since the LoginActivity is always the first page in the navigation stack,
        // we're exactly 1 level deep, and this is the only place that can Log Out,
        // we can simply 'finish' to navigate back naturally.
        // I had a hard time finding a way to artificially clear the navigation stack,
        // but I'm sure it can be done if it's ever needed.
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    //Handle menu item being clicked, i.e. the logout button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logoutMenu) {
            LogoutRequest logoutRequest = new LogoutRequest(this.authToken);
            LogoutTask logoutTask = new LogoutTask(loginPresenter, this);
            logoutTask.execute(logoutRequest);
        }
        return true;
    }

    @Override
    public void logoutSuccessful(LogoutResponse logoutResponse) {
        this.sendToast("Successfully logged out!");
        navigateAndClearStack();
    }

    @Override
    public void logoutUnsuccessful(LogoutResponse logoutResponse) {
        this.sendToast("Could not log out.");
    }

    @Override
    public void userFollowCountRetrieved(UserFollowCountResponse followCountResponse) {
        followerCount.setText(getString(R.string.followerCount, followCountResponse.getFollowers()));
        followeeCount.setText(getString(R.string.followeeCount, followCountResponse.getFollowees()));
    }

    @Override
    public void userFollowCountUnsuccessful(UserFollowCountResponse followCountResponse) {
        followerCount.setText("");
        followeeCount.setText("");
    }

    @Override
    public void handleException(Exception exception) {
        Log.e(LOG_TAG, exception.getMessage(), exception);
        this.sendToast(exception.getMessage());
    }
}