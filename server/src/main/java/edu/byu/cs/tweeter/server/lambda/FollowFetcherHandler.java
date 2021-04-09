package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
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


// Lambda Handler:      edu.byu.cs.tweeter.server.lambda.FollowFetcherHandler::handleRequest

public class FollowFetcherHandler implements RequestHandler<SQSEvent, Void> {

    AmazonSQSClientBuilder builder = AmazonSQSClientBuilder.standard().withRegion("us-west-2");
    AmazonSQS sqs = builder.build();
    String queueUrl = "https://sqs.us-west-2.amazonaws.com/137575193564/JobsQ";


    public void handleStatus(Status status) {
        //Save status to StatusDAO
        StatusDAO statusDAO = new StatusDAO();
        statusDAO.addStatus(status);

        //Get list of all followers to update status for
        FollowDAO followDAO = new FollowDAO();
        List<String> followers = followDAO.getFollowers(status.getUser().getAlias());
        List<FeedInsertionJob> feedInsertionJobs = new ArrayList<>();

        // Partition followers into chunks of 25
        int partitionSize = 25;
        List<List<Integer>> partitions = new ArrayList<List<Integer>>();
        for (int i = 0; i < followers.size(); i += partitionSize) {
            FeedInsertionJob job = new FeedInsertionJob();
            job.setAlias(status.getUser().getAlias());
            job.setTimestamp(status.getTimestampString());
            job.setListOfFollowers(followers.subList(i,
                    Math.min(i + partitionSize, followers.size())));
            feedInsertionJobs.add(job);
        }

        // Send all chunks of work to the job queue
        for (FeedInsertionJob job : feedInsertionJobs) {
            String jsonJob = JsonSerializer.serialize(job);
            SendMessageRequest sendMsgRequest = new SendMessageRequest()
                    .withQueueUrl(queueUrl).withMessageBody(jsonJob);
            SendMessageResult sendMsgResult = sqs.sendMessage(sendMsgRequest);
        }
    }

    @Override
    public Void handleRequest(SQSEvent event, Context context) {
        for (SQSEvent.SQSMessage msg : event.getRecords()) {
            Status status = JsonSerializer.deserialize(msg.getBody(), Status.class);

            handleStatus(status);
        }
        return null;
    }
}