package edu.byu.cs.tweeter.view.main;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.presenter.PostStatusPresenter;

public class PostStatusFragment extends Fragment {

    PostStatusPresenter.Fragment parentFragment;
    PostStatusPresenter presenter;
    FloatingActionButton sendStatusButton;
    FloatingActionButton cancelStatusButton;
    EditText statusText;

    public PostStatusFragment() {
        // Required empty public constructor
    }

    public static PostStatusFragment newInstance(PostStatusPresenter.Fragment parentFragment, User user, AuthToken authToken, PostStatusPresenter presenter) {
        PostStatusFragment fragment = new PostStatusFragment();
        fragment.presenter = presenter;
        fragment.parentFragment = parentFragment;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_post, container, false);

        statusText = view.findViewById(R.id.editText_status);
        statusText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                boolean valid = presenter.validatePost(s.toString());
                sendStatusButton.setEnabled(valid);
            }
        });

        sendStatusButton = view.findViewById(R.id.button_sendStatus);
        sendStatusButton.setEnabled(false);
        // Disable by default; wait until text is entered and validation process is complete
        // before enabling it again.
        sendStatusButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String messageBody = statusText.getText().toString();
                parentFragment.requestSendTweet(messageBody);
            }
        });

        cancelStatusButton = view.findViewById(R.id.cancelStatusButton);
        cancelStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentFragment.requestClose();
            }
        });

        return view;
    }
}