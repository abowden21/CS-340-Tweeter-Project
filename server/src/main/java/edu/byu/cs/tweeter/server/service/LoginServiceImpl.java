package edu.byu.cs.tweeter.server.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.S3BucketResource;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectIdBuilder;
import com.amazonaws.services.s3.model.UploadObjectRequest;
import com.amazonaws.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import edu.byu.cs.tweeter.server.DataAccessException;
import edu.byu.cs.tweeter.server.dao.AuthTokenDAO;
import edu.byu.cs.tweeter.server.dao.UserDAO;
import edu.byu.cs.tweeter.shared.model.domain.AuthToken;
import edu.byu.cs.tweeter.shared.model.domain.User;
import edu.byu.cs.tweeter.shared.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.model.request.LogoutRequest;
import edu.byu.cs.tweeter.shared.model.request.RegisterRequest;
import edu.byu.cs.tweeter.shared.model.response.LogoutResponse;
import edu.byu.cs.tweeter.shared.model.response.RegisterResponse;
import edu.byu.cs.tweeter.shared.model.service.LoginServiceInterface;
import edu.byu.cs.tweeter.shared.model.request.LoginRequest;
import edu.byu.cs.tweeter.shared.model.response.LoginResponse;

public class LoginServiceImpl implements LoginServiceInterface {

    AuthTokenDAO authTokenDao;
    UserDAO userDao;

    private String loginFailedMessage = "Error: Login Failed.";
    private String registerFailedMessage = "Error: Register Failed.";
    private String logoutFailedMessage = "Error: Logout Failed.";

    @Override
    public LoginResponse login(LoginRequest request) {
        if (request.getUsername().contains("fail")) {
            return new LoginResponse(loginFailedMessage);
        }
        User user = null;
        try {
            user = getUserDao().getUser(request.getUsername());
            String uniqueToken = "<Dummy unique token>";
            AuthToken authToken = new AuthToken(uniqueToken, user.getAlias());
            getAuthTokenDao().addAuthToken(authToken);
            return new LoginResponse(user, authToken);
        } catch (DataAccessException e) {
            return new LoginResponse(loginFailedMessage);
        }
    }

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        // Receive image bytes as B64 string, upload to S3, and return the URL
        String imageUrl = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
        if (!registerRequest.getImageBase64String().isEmpty()) {
            String bucketName = "jlsim-cs340-twitter-clone";
            AmazonS3 s3 = AmazonS3Client.builder().withRegion("us-east-1").build();
            String imgKey = registerRequest.getUsername() + "_" + LocalDateTime.now().toString() + ".jpg";
            byte[] imageBytes = Base64.decode(registerRequest.getImageBase64String());
            ByteArrayInputStream imageStream = new ByteArrayInputStream(imageBytes);
            PutObjectRequest s3Request =
                    new PutObjectRequest(bucketName, imgKey, imageStream, new ObjectMetadata())
                    .withCannedAcl(CannedAccessControlList.PublicRead);
            PutObjectResult s3Result = s3.putObject(s3Request);
            URL imageUrlObject = s3.getUrl(bucketName, imgKey);
            imageUrl = imageUrlObject.toString();
        }
        // Hash the password
        String hashedPassword;
        String salt;
        SecurePasswordService sps = new SecurePasswordService();
        try {
            SecurePasswordService.SecurePassword securePw = sps.hash(registerRequest.getPassword());
            hashedPassword = securePw.getHashedPassword();
            salt = securePw.getSalt();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // Create and store user
        User user = new User(registerRequest.getFirstName(), registerRequest.getLastName(),
                registerRequest.getUsername(), imageUrl);
        // TODO: set password & salt in user object
        try {
            getUserDao().addUser(user);
            String uniqueToken = "<Dummy unique token>";
            AuthToken authToken = new AuthToken(uniqueToken, user.getAlias());
            getAuthTokenDao().addAuthToken(authToken);
            return new RegisterResponse(user, authToken);
        } catch (DataAccessException e) {
            return new RegisterResponse(registerFailedMessage);
        }
    }

    @Override
    public LogoutResponse logout(LogoutRequest logoutRequest) {
        try {
            String token = logoutRequest.getAuthToken().getToken();
            getAuthTokenDao().deleteAuthToken(token);
            return new LogoutResponse(true);
        }
        catch (DataAccessException e) {
            return new LogoutResponse(logoutFailedMessage);
        }
    }

    public AuthTokenDAO getAuthTokenDao() {
        if (this.authTokenDao == null)
            this.authTokenDao = new AuthTokenDAO();
        return this.authTokenDao;
    }

    public UserDAO getUserDao() {
        if (this.userDao == null)
            this.userDao = new UserDAO();
        return this.userDao;
    }
}