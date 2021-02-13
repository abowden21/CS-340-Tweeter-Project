package edu.byu.cs.tweeter.model.domain;

import java.util.List;

public class Feed {
    private List<Status> statuses;

    public Feed(List<Status> statuses) {
        this.statuses = statuses;
    }

    public List<Status> getStatuses() {
        return statuses;
    }
}
