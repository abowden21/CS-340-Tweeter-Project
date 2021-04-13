package edu.byu.cs.tweeter.shared.model.domain;

import java.util.List;

public class FeedInsertionJob {
    private int jobNum;
    private String alias;
    private String timestamp;
    private List<String> listOfFollowers;

    public FeedInsertionJob() {}

    public FeedInsertionJob(String alias, String timestamp, List<String> listOfFollowers) {
        this.alias = alias;
        this.timestamp = timestamp;
        this.listOfFollowers = listOfFollowers;
    }

    public int getJobNum() {
        return jobNum;
    }

    public void setJobNum(int jobNum) {
        this.jobNum = jobNum;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public List<String> getListOfFollowers() {
        return listOfFollowers;
    }

    public void setListOfFollowers(List<String> listOfFollowers) {
        this.listOfFollowers = listOfFollowers;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
