package edu.byu.cs.tweeter.server.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import edu.byu.cs.tweeter.server.DataAccessException;
import edu.byu.cs.tweeter.shared.model.domain.Status;

public class StatusDAOTests {
    StatusDAO statusDao;

    @BeforeEach
    void setup() {
        statusDao = new StatusDAO();
    }

    @Test
    void dev_test() throws DataAccessException {
        List<Status> story = statusDao.getUserStory("doge", "2021-04-05T17:24:16.0", 3);
        for (Status status : story) {
            System.out.println("User: " + status.getUser().getAlias() + "\t Time: " + status.getTimestampString());
            System.out.println("Message: \"" + status.getMessage() + "\"");
            System.out.println();
        }
    }
}
