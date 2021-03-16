package edu.byu.cs.tweeter.shared.model.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Status {
    private LocalDateTime timeStamp;
    private String message;
    private List<String> urls;
    private List<String> mentions;
    private User associatedUser;

    public Status(){}

    public Status(LocalDateTime timeStamp, String message, User associatedUser) {
        this.timeStamp = timeStamp;
        this.message = message;
        this.associatedUser = associatedUser;
        this.urls = new ArrayList<String>();
        this.mentions = new ArrayList<String>();
    }

    public Status(LocalDateTime timeStamp, String message, List<String> urls, List<String> mentions, User associatedUser) {
        this.timeStamp = timeStamp;
        this.message = message;
        this.urls = urls;
        this.mentions = mentions;
        this.associatedUser = associatedUser;
    }
    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }
    public void setTimestamp(LocalDateTime ts) {
        this.timeStamp = ts;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getUrls() {
        return urls;
    }
    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public List<String> getMentions() {
        return mentions;
    }
    public void setMentions(List<String> mentions) {
        this.mentions = mentions;
    }

    public User getUser() {
        return associatedUser;
    }
    public void setUser(User user) {
        this.associatedUser = user;
    }

}
