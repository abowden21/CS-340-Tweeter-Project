package edu.byu.cs.tweeter.server.dao;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.shared.model.domain.FeedInsertionJob;
import edu.byu.cs.tweeter.shared.model.domain.Status;
import edu.byu.cs.tweeter.shared.model.domain.User;

public class Test_DeleteLater {

    @Test
    void test_chunks() {
        List<String> followers = new ArrayList<>();
        for (int i = 0; i < 19; i++) {
            followers.add("Follower#" + i);
        }

        Status status = new Status("timestamp", "message", new User("first", "last", "posterAlias", null));

        List<FeedInsertionJob> feedInsertionJobs = new ArrayList<>();

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

        System.out.println("Test");
    }
}
