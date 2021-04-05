package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.server.dao.FollowDAO;
import edu.byu.cs.tweeter.server.dao.FollowersDAO;
import edu.byu.cs.tweeter.server.dao.StatusDAO;
import edu.byu.cs.tweeter.shared.model.domain.FeedInsertionJob;
import edu.byu.cs.tweeter.shared.model.domain.Status;
import edu.byu.cs.tweeter.shared.model.net.JsonSerializer;
import edu.byu.cs.tweeter.shared.model.request.FollowersRequest;

public class FollowFetcherHandler implements RequestHandler<SQSEvent, Void> {

    AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
    String queueUrl = "https://sqs.us-west-2.amazonaws.com/137575193564/JobsQ";

    @Override
    public Void handleRequest(SQSEvent event, Context context) {
        for (SQSEvent.SQSMessage msg : event.getRecords()) {
            Status status = JsonSerializer.deserialize(msg.getBody(), Status.class);

            //Save status to StatusDAO
            StatusDAO statusDAO = new StatusDAO();
            statusDAO.addStatus(status);

            //Get list of all followers to update status for
            FollowDAO followDAO = new FollowDAO();
            List<String> followers = followDAO.getFollowers(status.getUser().getAlias());

            int currentPosition = 0;

            List<String> subList = new ArrayList<>();
            for (;currentPosition < followers.size(); currentPosition++) {
                subList.add(followers.get(currentPosition));

                if (currentPosition % 25 == 0) {
                    break;
                }
            }

            for (int i = 0; i < followers.size(); i++) {
                if ((i > 0 && i % 25) || i == followers.size() - 1) {
                    FeedInsertionJob job = new FeedInsertionJob();
                    int first = (i - 25 > 0) ? i - 25 : 0;
                    int last = i;
                    job.users = followers.sublist(first, last);
                    job.alias = status.getUser().getAlias();
                    // Timestamp
                }
            }


            //Post the status to JobsQ
            List<FeedInsertionJob> feedInsertionJobs = new ArrayList<>();




            for (FeedInsertionJob job : feedInsertionJobs) {
                String messageBody = JsonSerializer.serialize(status);
                SendMessageRequest sendMsgRequest = new SendMessageRequest()
                        .withQueueUrl(queueUrl).withMessageBody(messageBody);
                SendMessageResult sendMsgResult = sqs.sendMessage(sendMsgRequest);
            }



            //FeedInsertionJob Model
            //StatusID
            //List of follower Alias max 25
        }
        return null;
    }}
