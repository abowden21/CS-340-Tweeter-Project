package edu.byu.cs.tweeter.model.domain;

import java.util.List;

public class Story {
    private List<Status> statuses;

    public Story(List<Status> statuses) {
        this.statuses = statuses;
    }

    public List<Status> getStatuses() {
        return statuses;
    }
}
