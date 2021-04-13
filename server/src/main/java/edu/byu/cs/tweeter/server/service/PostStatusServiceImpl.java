package edu.byu.cs.tweeter.server.service;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;

import java.time.LocalDateTime;

import edu.byu.cs.tweeter.server.DataAccessException;
import edu.byu.cs.tweeter.server.dao.AuthTokenDAO;
import edu.byu.cs.tweeter.server.dao.UserDAO;
import edu.byu.cs.tweeter.shared.model.domain.AuthToken;
import edu.byu.cs.tweeter.shared.model.domain.Status;
import edu.byu.cs.tweeter.shared.model.domain.User;
import edu.byu.cs.tweeter.shared.model.net.JsonSerializer;
import edu.byu.cs.tweeter.shared.model.request.PostStatusRequest;
import edu.byu.cs.tweeter.shared.model.response.PostStatusResponse;
import edu.byu.cs.tweeter.shared.model.service.PostStatusServiceInterface;

public class PostStatusServiceImpl implements PostStatusServiceInterface {
//    StatusDAO statusDao;
    UserDAO userDao;
    AuthTokenDAO authTokenDao;
    AmazonSQS sqs;
    String queueUrl = "https://sqs.us-west-2.amazonaws.com/137575193564/PostsQ";

    private String failedMessage = "Client Error: Failed to send status. User may not be authenticated.";
    private String failedAuthTokenInvalidMessage = "Client Error: Failed to send status; auth token invalid.";
    private String failedServerMessage = "Server Error: Server failed";

    public PostStatusServiceImpl() {
    }

    public AmazonSQS getSqs() {
        if (sqs == null) {
            AmazonSQSClientBuilder builder = AmazonSQSClientBuilder.standard()
                    .withRegion("us-west-2");
            sqs = builder.build();
        }
        return sqs;
    }

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
            System.out.println("Auth token is valid: " + authToken.getToken() + ". User alias: " + authToken.getUserAlias());
            User user = getUserDao().getUser(authToken.getUserAlias());
            System.out.println("Found user: " + user.getAlias());
            // Save status
            LocalDateTime timestamp = LocalDateTime.now();
            Status status = new Status(timestamp, postStatusRequest.getMessage(), user);

            String messageBody = JsonSerializer.serialize(status);
            System.out.println("Serialized message for queue: " + messageBody);

            SendMessageRequest sendMsgRequest = new SendMessageRequest()
                    .withQueueUrl(queueUrl).withMessageBody(messageBody);

            SendMessageResult sendMsgResult = getSqs().sendMessage(sendMsgRequest);
            System.out.println("Sent message to queue.");

            PostStatusResponse response = new PostStatusResponse(status);
            return response;
        }
        catch (DataAccessException e) {
            System.out.println(e.toString());
            throw new RuntimeException(failedMessage);
        }
        catch (Exception e) {
            System.out.println(e.toString());
            throw new RuntimeException(failedServerMessage);
        }
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
