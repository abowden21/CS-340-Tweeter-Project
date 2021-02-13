package edu.byu.cs.tweeter.model.domain;

import java.time.LocalDateTime;
import java.util.List;

public class Status {
    private final LocalDateTime timeStamp;
    private final String message;
    private final List<String> urls;
    private final List<String> mentions;

    public Status(LocalDateTime timeStamp, String message, List<String> urls, List<String> mentions) {
        this.timeStamp = timeStamp;
        this.message = message;
        this.urls = urls;
        this.mentions = mentions;
    }
    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getUrls() {
        return urls;
    }

    public List<String> getMentions() {
        return mentions;
    }


}
