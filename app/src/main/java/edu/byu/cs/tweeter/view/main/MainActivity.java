package edu.byu.cs.tweeter.view.main;

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

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;
import edu.byu.cs.tweeter.model.service.response.PostStatusResponse;
import edu.byu.cs.tweeter.presenter.LoginPresenter;
import edu.byu.cs.tweeter.presenter.PostStatusPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.LogoutTask;
import edu.byu.cs.tweeter.view.asyncTasks.PostStatusTask;
import edu.byu.cs.tweeter.view.util.ImageUtils;

/**
 * The main activity for the application. Contains tabs for feed, story, following, and followers.
 */
public class MainActivity extends AppCompatActivity implements LoginPresenter.View, LogoutTask.Observer, PostStatusPresenter.Fragment, PostStatusTask.Observer {
    private static final String LOG_TAG = "MainActivity";
    public static final String CURRENT_USER_KEY = "CurrentUser";
    public static final String AUTH_TOKEN_KEY = "AuthTokenKey";

    private LoginPresenter presenter;
    private Toast currentToast;

    FragmentManager fragmentManager;
    FrameLayout postStatusFrame;
    FloatingActionButton postStatusButton;
    PostStatusFragment postFragment;
    PostStatusPresenter postPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Same presenter as LoginActivity.
        presenter = new LoginPresenter(this);

        User user = (User) getIntent().getSerializableExtra(CURRENT_USER_KEY);
        if(user == null) {
            throw new RuntimeException("User not passed to activity");
        }

        AuthToken authToken = (AuthToken) getIntent().getSerializableExtra(AUTH_TOKEN_KEY);

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
                // Create PostFragment if it hasn't been created
                if (postFragment == null) {
                    postFragment = PostStatusFragment.newInstance(MainActivity.this, user, authToken, postPresenter);
                }
                showPostFragment();
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        TextView userName = findViewById(R.id.userName);
        userName.setText(user.getName());

        TextView userAlias = findViewById(R.id.userAlias);
        userAlias.setText(user.getAlias());

        ImageView userImageView = findViewById(R.id.userImage);
        userImageView.setImageDrawable(ImageUtils.drawableFromByteArray(user.getImageBytes()));

        TextView followeeCount = findViewById(R.id.followeeCount);
        followeeCount.setText(getString(R.string.followeeCount, 42));

        TextView followerCount = findViewById(R.id.followerCount);
        followerCount.setText(getString(R.string.followerCount, 27));
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
        PostStatusRequest postStatusRequest = new PostStatusRequest(message);
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
            LogoutRequest logoutRequest = new LogoutRequest();
            LogoutTask logoutTask = new LogoutTask(presenter, this);
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
    public void handleException(Exception exception) {
        Log.e(LOG_TAG, exception.getMessage(), exception);
        this.sendToast(exception.getMessage());
    }
}