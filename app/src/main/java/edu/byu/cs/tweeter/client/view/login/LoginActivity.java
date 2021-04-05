package edu.byu.cs.tweeter.client.view.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.shared.model.domain.AuthToken;
import edu.byu.cs.tweeter.shared.model.domain.User;
import edu.byu.cs.tweeter.shared.model.request.LoginRequest;
import edu.byu.cs.tweeter.shared.model.request.RegisterRequest;
import edu.byu.cs.tweeter.shared.model.response.LoginResponse;
import edu.byu.cs.tweeter.shared.model.response.RegisterResponse;
import edu.byu.cs.tweeter.client.presenter.LoginPresenter;
import edu.byu.cs.tweeter.client.util.ByteArrayUtils;
import edu.byu.cs.tweeter.client.view.asyncTasks.LoginTask;
import edu.byu.cs.tweeter.client.view.asyncTasks.RegisterTask;
import edu.byu.cs.tweeter.client.view.main.MainActivity;

/**
 * Contains the minimum UI required to allow the user to login with a hard-coded user. Most or all
 * of this should be replaced when the back-end is implemented.
 */
// Note: Uses LoginPresenter.View for both functions, but has separate Task.Observer class for each.
public class LoginActivity extends AppCompatActivity implements LoginPresenter.View, LoginTask.Observer, RegisterTask.Observer {

    private static final String LOG_TAG = "LoginActivity";

    private LoginPresenter presenter;
    private Toast statusToast;
    private Button loginButton;
    private Button registerButton;
    private EditText usernameText;
    private EditText passwordText;
    private EditText firstNameText;
    private EditText lastNameText;
    private Button chooseImageButton;

    private final int RESULT_LOAD_IMAGE = 101; // Arbitrary number
    private Uri imageUri;
    private byte[] imageBytes = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        presenter = new LoginPresenter(this);

        loginButton = findViewById(R.id.login_SignIn);
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                disableButtons();
                statusToast = Toast.makeText(LoginActivity.this, "Logging in...", Toast.LENGTH_LONG);
                statusToast.show();

                LoginRequest loginRequest = new LoginRequest(usernameText.getText().toString(), passwordText.getText().toString());
                LoginTask loginTask = new LoginTask(presenter, LoginActivity.this);
                loginTask.execute(loginRequest);
            }
        });

        registerButton = findViewById(R.id.login_Register);
        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                disableButtons();
                statusToast = Toast.makeText(LoginActivity.this, "Creating new account...", Toast.LENGTH_LONG);
                statusToast.show();

                try {
                    loadImageBytes();
                    RegisterRequest registerRequest =
                            new RegisterRequest(usernameText.getText().toString(), passwordText.getText().toString(),
                                    firstNameText.getText().toString(), lastNameText.getText().toString(),
                                    imageBytes);
                    RegisterTask registerTask = new RegisterTask(presenter, LoginActivity.this);
                    registerTask.execute(registerRequest);
                } catch (IOException e) {
                    handleException(e);
                }
            }
        });

        TextWatcher dataValidationWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void afterTextChanged(Editable s) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateButtonsEnabledStatus();
            }
        };

        usernameText = findViewById(R.id.editText_user);
        usernameText.addTextChangedListener(dataValidationWatcher);
        passwordText = findViewById(R.id.editText_pw);
        passwordText.addTextChangedListener(dataValidationWatcher);
        firstNameText = findViewById(R.id.editText_first);
        firstNameText.addTextChangedListener(dataValidationWatcher);
        lastNameText = findViewById(R.id.editText_last);
        lastNameText.addTextChangedListener(dataValidationWatcher);
        chooseImageButton = findViewById(R.id.login_choosePhoto);
        chooseImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch gallery image selector
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_LOAD_IMAGE);
                // Results will be returned in a separate callback.
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            // Handle image selection.
            this.imageBytes = null;
            this.imageUri = data.getData();
            updateButtonsEnabledStatus();
        }
    }

    private void loadImageBytes() throws IOException {
        if (this.imageBytes == null) {
            InputStream uriInputStream = getContentResolver().openInputStream(imageUri);
            this.imageBytes = ByteArrayUtils.bytesFromInputStream(uriInputStream);
        }
    }

    private void clearAllFields() {
        usernameText.setText("");
        passwordText.setText("");
        firstNameText.setText("");
        lastNameText.setText("");
        imageBytes = null;
        imageUri = null;
        usernameText.requestFocus();
    }

    private void disableButtons() {
        loginButton.setEnabled(false);
        registerButton.setEnabled(false);
        chooseImageButton.setEnabled(false);
    }

    private void updateButtonsEnabledStatus() {
        boolean enableSignIn = usernameText.getText().toString().length() > 0
                && passwordText.getText().toString().length() > 0;
        boolean imageSelected = imageUri != null;
        boolean enableRegister = enableSignIn && imageSelected && presenter.validateFields(
                usernameText.getText().toString(), passwordText.getText().toString(),
                firstNameText.getText().toString(), lastNameText.getText().toString());
        loginButton.setEnabled(enableSignIn);
        registerButton.setEnabled(enableRegister);
        chooseImageButton.setEnabled(true);
    }

    private void authenticateUserAndNavigate(User user, AuthToken authToken) {
        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtra(MainActivity.CURRENT_USER_KEY, user);
        intent.putExtra(MainActivity.AUTH_TOKEN_KEY, authToken);

        if (statusToast != null) {
            statusToast.cancel();
        }
        startActivity(intent);
    }

    @Override
    public void loginSuccessful(LoginResponse loginResponse) {
        clearAllFields();
        updateButtonsEnabledStatus();
        authenticateUserAndNavigate(loginResponse.getUser(), loginResponse.getAuthToken());
    }

    @Override
    public void loginUnsuccessful(LoginResponse loginResponse) {
        clearAllFields();
        updateButtonsEnabledStatus();
        Toast.makeText(this, "Failed to login. " + loginResponse.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void registerSuccessful(RegisterResponse registerResponse) {
        updateButtonsEnabledStatus();
        authenticateUserAndNavigate(registerResponse.getUser(), registerResponse.getAuthToken());
    }

    @Override
    public void registerUnsuccessful(RegisterResponse registerResponse) {
        updateButtonsEnabledStatus();
        Toast.makeText(this, "Failed to register. " + registerResponse.getMessage(), Toast.LENGTH_LONG).show();
    }

    /**
     * A callback indicating that an exception was thrown in an asynchronous method called on the
     * presenter.
     *
     * @param exception the exception.
     */
    @Override
    public void handleException(Exception exception) {
        Log.e(LOG_TAG, exception.getMessage(), exception);
        Toast.makeText(this, "Failed to login because of exception: " + exception.getMessage(), Toast.LENGTH_LONG).show();
    }
}
