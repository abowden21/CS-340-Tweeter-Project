package edu.byu.cs.tweeter.shared.model.domain;

import java.io.Serializable;

import java.util.Objects;

/**
 * Represents a user in the system.
 */
public class User implements Comparable<User>, Serializable {

    private String firstName;
    private String lastName;
    private String alias;
    private String imageUrl;
    private byte [] imageBytes;
    private Story story;
    private Feed feed;

    public User() {}

    public User(String firstName, String lastName, String imageURL) {
        this(firstName, lastName, String.format("@%s%s", firstName, lastName), imageURL, null, null);
    }

    public User(String firstName, String lastName, String alias, String imageURL) {
        this(firstName, lastName, alias, imageURL, null, null);
    }

    public User(String firstName, String lastName, String alias, String imageURL, Story story, Feed feed) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.alias = alias;
        this.imageUrl = imageURL;
        this.story = story;
        this.feed = feed;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getName() {
        return String.format("%s %s", firstName, lastName);
    }

    public String getAlias() {
        return alias;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public byte [] getImageBytes() {
        return imageBytes;
    }

    public Story getStory() {
        return story;
    }

    public Feed getFeed() {
        return feed;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setStory(Story story) {
        this.story = story;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return alias.equals(user.alias);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alias);
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", alias='" + alias + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    @Override
    public int compareTo(User user) {
        return this.getAlias().compareTo(user.getAlias());
    }
}
