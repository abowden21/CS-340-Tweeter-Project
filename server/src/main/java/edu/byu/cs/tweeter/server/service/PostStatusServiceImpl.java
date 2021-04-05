package edu.byu.cs.tweeter.server.service;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;

import java.time.LocalDateTime;

import javax.xml.crypto.Data;

import edu.byu.cs.tweeter.server.DataAccessException;
import edu.byu.cs.tweeter.server.dao.AuthTokenDAO;
import edu.byu.cs.tweeter.server.dao.StatusDAO;
import edu.byu.cs.tweeter.server.dao.UserDAO;
import edu.byu.cs.tweeter.shared.model.domain.AuthToken;
import edu.byu.cs.tweeter.shared.model.domain.Status;
import edu.byu.cs.tweeter.shared.model.domain.User;
import edu.byu.cs.tweeter.shared.model.net.JsonSerializer;
import edu.byu.cs.tweeter.shared.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.model.request.PostStatusRequest;
import edu.byu.cs.tweeter.shared.model.response.PostStatusResponse;
import edu.byu.cs.tweeter.shared.model.service.PostStatusServiceInterface;

public class PostStatusServiceImpl implements PostStatusServiceInterface {
    StatusDAO statusDao;
    UserDAO userDao;
    AuthTokenDAO authTokenDao;
    AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
    String queueUrl = "https://sqs.us-west-2.amazonaws.com/137575193564/PostsQ";

    private String failedMessage = "Client Error: Failed to send status. User may not be authenticated.";
    private String failedAuthTokenInvalidMessage = "Client Error: Failed to send status; auth token invalid.";
    private String failedServerMessage = "Server Error: Server failed";

    @Override
    public PostStatusResponse sendStatus(PostStatusRequest postStatusRequest) {
        try {
            // Validate authToken, get user
            AuthToken authToken = getAuthTokenDao().getAuthToken(postStatusRequest.getAuthToken());
            if (authToken.getExpirationDateTime().isBefore(LocalDateTime.now())) {
                // AuthToken has expired; delete it and return a failed response.
                getAuthTokenDao().deleteAuthToken(authToken.getToken());
                throw new RuntimeException(failedAuthTokenInvalidMessage);
            }
            User user = getUserDao().getUser(authToken.getUserAlias());
            // Save status
            LocalDateTime timestamp = LocalDateTime.now();
            Status status = new Status(timestamp, postStatusRequest.getMessage(), user);

            String messageBody = JsonSerializer.serialize(status);

            SendMessageRequest sendMsgRequest = new SendMessageRequest()
                    .withQueueUrl(queueUrl).withMessageBody(messageBody);

            SendMessageResult sendMsgResult = sqs.sendMessage(sendMsgRequest);

            PostStatusResponse response = new PostStatusResponse(status);
            return response;
        }
        catch (DataAccessException e) {
            throw new RuntimeException(failedMessage);
        }
        catch (Exception e) {
            throw new RuntimeException(failedServerMessage);
        }
    }

    public StatusDAO getStatusDao() {
        if (this.statusDao == null)
            this.statusDao = new StatusDAO();
        return this.statusDao;
    }

    public UserDAO getUserDao() {
        if (this.userDao == null)
            this.userDao = new UserDAO();
        return this.userDao;
    }

    public AuthTokenDAO getAuthTokenDao() {
        if (this.authTokenDao == null)
            this.authTokenDao = new AuthTokenDAO();
        return this.authTokenDao;
    }
}
