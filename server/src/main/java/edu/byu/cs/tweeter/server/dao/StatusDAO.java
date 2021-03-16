package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.shared.model.domain.Status;

public class StatusDAO {
    public StatusDAO() {}

    public void addStatus(Status status){
        // Log for now so we can inspect in CloudWatch
        System.out.println("Status posted " + status.getTimestampString() + ": " + status.getMessage());
    }
//    public Status getStatus(LocalDateTime timestamp)
//    public List<Status> getStatuses(String userAlias, int limit)
//    public void updateStatus(String token, Status authToken) {}
//    public void deleteStatus(String token) {}

    // TODO: solve pagination/identification for this class. (really more of a Milestone 4 issue)
}
