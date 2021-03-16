package edu.byu.cs.tweeter.shared.model.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Status {
//    private LocalDateTime timestamp;
    private String timestampString;
    private String message;
    private List<String> urls;
    private List<String> mentions;
    private User associatedUser;

    public Status(){}

    public Status(LocalDateTime timestamp, String message, User associatedUser) {
        this(timestamp, message, new ArrayList<String>(), new ArrayList<String>(), associatedUser);
    }

    public Status(String timestamp, String message, User associatedUser) {
        this(timestamp, message, new ArrayList<String>(), new ArrayList<String>(), associatedUser);
    }

    public Status(LocalDateTime timestamp, String message, List<String> urls, List<String> mentions, User associatedUser) {
        setTimestamp(timestamp);
        this.message = message;
        this.urls = urls;
        this.mentions = mentions;
        this.associatedUser = associatedUser;
    }

    public Status(String timestamp, String message, List<String> urls, List<String> mentions, User associatedUser) {
        this.timestampString = timestamp;
        this.message = message;
        this.urls = urls;
        this.mentions = mentions;
        this.associatedUser = associatedUser;
    }

    public String getTimestampString() {
        return timestampString;
    }
    public void setTimestampString(String ts) {
        this.timestampString = ts;
    }

    public LocalDateTime getTimestamp() {
        return LocalDateTime.parse(this.timestampString);
    }
    public void setTimestamp(LocalDateTime dateTime) {
        this.timestampString = dateTime.toString();
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
