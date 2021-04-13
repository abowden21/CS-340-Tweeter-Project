package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;

import edu.byu.cs.tweeter.server.dao.FeedDAO;
import edu.byu.cs.tweeter.shared.model.domain.FeedInsertionJob;
import edu.byu.cs.tweeter.shared.model.net.JsonSerializer;

// Lambda Handler:      edu.byu.cs.tweeter.server.lambda.FeedInserterHandler::handleRequest

public class FeedInserterHandler implements RequestHandler<SQSEvent, Void> {

    @Override
    public Void handleRequest(SQSEvent event, Context context) {
        FeedDAO feedDao = new FeedDAO();

        int numTimesLooped = 0;
        for (SQSEvent.SQSMessage msg : event.getRecords()) {
            numTimesLooped++;
            System.out.println("Loop num " + numTimesLooped + ". Message body:\n" + msg.getBody());
            FeedInsertionJob job = JsonSerializer.deserialize(msg.getBody(), FeedInsertionJob.class);
            // Take out all follower aliases, make a batch write to dynamo
            feedDao.batchAddStatusToFeed(job.getListOfFollowers(), job.getAlias(), job.getTimestamp());
        }

        return null;
    }
}
