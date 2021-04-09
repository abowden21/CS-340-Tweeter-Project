package edu.byu.cs.tweeter.server.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class FollowDAOTests {

    private FollowDAO followDAO;

    @BeforeEach
    void setup() {
        followDAO = new FollowDAO();
    }

    @Test
    void test_dev() {

        List<String> followers = followDAO.getFollowers("doge");


        assert followers.size() == 2;
        // TODO: write real tests
    }

}
