package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.document.Index;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.byu.cs.tweeter.shared.model.request.FollowRequest;
import edu.byu.cs.tweeter.shared.model.request.FollowStatusRequest;
import edu.byu.cs.tweeter.shared.model.request.UserFollowCountRequest;
import edu.byu.cs.tweeter.shared.model.response.FollowResponse;
import edu.byu.cs.tweeter.shared.model.response.FollowStatusResponse;
import edu.byu.cs.tweeter.shared.model.response.UserFollowCountResponse;

public class FollowDAO extends BaseDynamoDAO {

    private final String followeeHandleAttribute = "followee_handle";
    private final String followerHandleAttribute = "follower_handle";

    public FollowDAO() {
        super("follows");
    }

    public FollowResponse setFollow(FollowRequest request) {

        try {
            getTable().putItem(new Item().withPrimaryKey(followerHandleAttribute,
                    request.getUserAlias(), followeeHandleAttribute, request.getFollowerAlias()));
        } catch(Exception e) {
            return new FollowResponse(false, true);
        }

        return new FollowResponse(true, true);
    }

    public FollowResponse setUnfollow(FollowRequest request) {
        return new FollowResponse(true, false);
    }

    public FollowStatusResponse getFollowStatus(FollowStatusRequest request) {
        return new FollowStatusResponse(true, true);
    }

    public UserFollowCountResponse getUserFollowCount(UserFollowCountRequest request) {
        return new UserFollowCountResponse(100, 99);
    }

    public List<String> getFollowers(String userAlias) {
        List<String> followers = new ArrayList<>();

        Index index = getTable().getIndex("follows_index");
        for (Item item : index.query(
                new QuerySpec().withHashKey(followeeHandleAttribute, userAlias)
                .withAttributesToGet(followerHandleAttribute))) {

            followers.add(item.getString(followeeHandleAttribute));
        }

        return followers;
    }

    public List<String> getFollowees(String userAlias) {
        //TODO: do it yo
        return null;
    }
}
