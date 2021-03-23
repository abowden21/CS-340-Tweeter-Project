package edu.byu.cs.tweeter.server;

public class DataAccessException extends Exception {
    public DataAccessException(){}
    public DataAccessException(String message) {
        super(message);
    }
}
