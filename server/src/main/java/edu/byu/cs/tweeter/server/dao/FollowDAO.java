package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.document.GetItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Index;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.management.Query;

import edu.byu.cs.tweeter.server.DataAccessException;
import edu.byu.cs.tweeter.server.utils.AuthTokenValidator;
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
        getTable().putItem(new Item().withPrimaryKey(followerHandleAttribute,
                request.getUserAlias(), followeeHandleAttribute, request.getFollowerAlias()));

        return new FollowResponse(true, true);
    }

    public FollowResponse setUnfollow(FollowRequest request) {
        DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
                .withPrimaryKey(new PrimaryKey(followerHandleAttribute,
                        request.getUserAlias(), followeeHandleAttribute, request.getFollowerAlias()));


        System.out.println("Attempting a conditional delete...");
        getTable().deleteItem(deleteItemSpec);
        System.out.println("DeleteItem succeeded");

        return new FollowResponse(true, false);
    }

    public FollowStatusResponse getFollowStatus(FollowStatusRequest request) {
        GetItemSpec spec = new GetItemSpec().withPrimaryKey(followerHandleAttribute,
                request.getLoggedInUserAlias(), followeeHandleAttribute, request.getOtherUserAlias());

        Item item = getTable().getItem(spec);

        //No follow relationship exists
        if (item == null) {
            return new FollowStatusResponse(false, true);
        }

        //Follow exists and is correct
        else if (item.getString(followerHandleAttribute).equals(request.getLoggedInUserAlias()) &&
                item.getString(followeeHandleAttribute).equals(request.getOtherUserAlias())) {
            return new FollowStatusResponse(true, true);
        }

        return new FollowStatusResponse(false, false);
    }

    public UserFollowCountResponse getUserFollowCount(UserFollowCountRequest request) {
        int followerCount = getFollowers(request.getAlias()).size();
        int followeeCount = getFollowees(request.getAlias()).size();

        return new UserFollowCountResponse(followerCount, followeeCount);
    }

    public List<String> getFollowers(String userAlias) {
        List<String> followers = new ArrayList<>();

        Index index = getTable().getIndex("follows_index");
        for (Item item : index.query(
                new QuerySpec().withHashKey(followeeHandleAttribute, userAlias)
                .withAttributesToGet(followerHandleAttribute))) {

            followers.add(item.getString(followerHandleAttribute));
        }

        return followers;
    }

    public List<String> getFollowees(String userAlias) {
        List<String> followees = new ArrayList<>();

        for (Item item : getTable().query(
                new QuerySpec().withHashKey(followerHandleAttribute, userAlias)
                        .withAttributesToGet(followeeHandleAttribute))) {

            followees.add(item.getString(followeeHandleAttribute));
        }

        return followees;
    }
}
