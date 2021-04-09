package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.byu.cs.tweeter.server.lambda.FollowFetcherHandler;
import edu.byu.cs.tweeter.shared.model.domain.Status;
import edu.byu.cs.tweeter.shared.model.domain.User;

public class FollowFetcherHandlerTest {
    @BeforeEach
    void setup() {

    }

    @Test
    void test() {
        FollowFetcherHandler handler = new FollowFetcherHandler();
        User user = new User();
        user.setAlias("doge");
        Status status = new Status("2021-04-08T19:09:35.617176", "Test Status", user);
        handler.handleStatus(status);
    }
}
