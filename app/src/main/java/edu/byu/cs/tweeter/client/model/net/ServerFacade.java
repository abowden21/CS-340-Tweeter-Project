package edu.byu.cs.tweeter.client.model.net;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.byu.cs.tweeter.BuildConfig;
import edu.byu.cs.tweeter.shared.model.domain.AuthToken;
import edu.byu.cs.tweeter.shared.model.domain.Status;
import edu.byu.cs.tweeter.shared.model.domain.User;
import edu.byu.cs.tweeter.shared.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.model.request.FeedRequest;
import edu.byu.cs.tweeter.shared.model.request.FollowRequest;
import edu.byu.cs.tweeter.shared.model.request.FollowStatusRequest;
import edu.byu.cs.tweeter.shared.model.request.FollowersRequest;
import edu.byu.cs.tweeter.shared.model.request.FollowingRequest;
import edu.byu.cs.tweeter.shared.model.request.GetUserRequest;
import edu.byu.cs.tweeter.shared.model.request.LoginRequest;
import edu.byu.cs.tweeter.shared.model.request.LogoutRequest;
import edu.byu.cs.tweeter.shared.model.request.PostStatusRequest;
import edu.byu.cs.tweeter.shared.model.request.RegisterRequest;
import edu.byu.cs.tweeter.shared.model.request.StoryRequest;
import edu.byu.cs.tweeter.shared.model.request.UserFollowCountRequest;
import edu.byu.cs.tweeter.shared.model.response.FeedResponse;
import edu.byu.cs.tweeter.shared.model.response.FollowResponse;
import edu.byu.cs.tweeter.shared.model.response.FollowStatusResponse;
import edu.byu.cs.tweeter.shared.model.response.FollowersResponse;
import edu.byu.cs.tweeter.shared.model.response.FollowingResponse;
import edu.byu.cs.tweeter.shared.model.response.UserFollowCountResponse;
import edu.byu.cs.tweeter.shared.model.response.GetUserResponse;
import edu.byu.cs.tweeter.shared.model.response.LoginResponse;
import edu.byu.cs.tweeter.shared.model.response.LogoutResponse;
import edu.byu.cs.tweeter.shared.model.response.PostStatusResponse;
import edu.byu.cs.tweeter.shared.model.response.RegisterResponse;
import edu.byu.cs.tweeter.shared.model.response.StoryResponse;

/**
 * Acts as a Facade to the Tweeter server. All network requests to the server should go through
 * this class.
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class ServerFacade {

    // TODO: Set this to the invoke URL of your API. Find it by going to your API in AWS, clicking
    //  on stages in the right-side menu, and clicking on the stage you deployed your API to.
    private static final String SERVER_URL = "https://uv5xi7355k.execute-api.us-west-2.amazonaws.com/production";

    private final ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);

    /**
     * Performs a login and if successful, returns the logged in user and an auth token.
     *
     * @param request contains all information needed to perform a login.
     * @return the login response.
     */
    public LoginResponse login(LoginRequest request, String urlPath) throws IOException, TweeterRemoteException {
        LoginResponse response = clientCommunicator.doPost(urlPath, request, null, LoginResponse.class);

        if(response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }

    public RegisterResponse register(RegisterRequest request, String urlPath) throws IOException, TweeterRemoteException {
        RegisterResponse response = clientCommunicator.doPost(urlPath, request, null, RegisterResponse.class);

        if(response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }

    public LogoutResponse logout(LogoutRequest request, String urlPath) throws IOException, TweeterRemoteException {
        LogoutResponse response = clientCommunicator.doPost(urlPath, request, null, LogoutResponse.class);

        if(response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request.
     *
     * @param request contains information about the user whose followees are to be returned and any
     *                other information required to satisfy the request.
     * @return the followees.
     */
    public FollowingResponse getFollowees(FollowingRequest request, String urlPath)
            throws IOException, TweeterRemoteException {
        FollowingResponse response = clientCommunicator.doPost(urlPath, request, null, FollowingResponse.class);

        if(response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }

    public FollowResponse setFollow(FollowRequest request, String urlPath) throws IOException, TweeterRemoteException {
        FollowResponse response = clientCommunicator.doPost(urlPath, request, null, FollowResponse.class);

        if(response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }

    public UserFollowCountResponse getUserFollowCount(UserFollowCountRequest request, String urlPath) throws IOException, TweeterRemoteException {
        UserFollowCountResponse response = clientCommunicator.doPost(urlPath, request, null, UserFollowCountResponse.class);

        if(response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }

    public FollowStatusResponse getFollowStatus(FollowStatusRequest request, String urlPath) throws IOException, TweeterRemoteException {
        FollowStatusResponse response = clientCommunicator.doPost(urlPath, request, null, FollowStatusResponse.class);

        if(response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }

    public PostStatusResponse sendStatus(PostStatusRequest postStatusRequest, String urlPath) throws IOException, TweeterRemoteException {
        PostStatusResponse response = clientCommunicator.doPost(urlPath, postStatusRequest, null, PostStatusResponse.class);
        if(response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }

    // OLD DEPRECATED CODE. TODO DELETE

    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";

    private final User user1 = new User("Allen", "Anderson", MALE_IMAGE_URL);
    private final User user2 = new User("Amy", "Ames", FEMALE_IMAGE_URL);
    private final User user3 = new User("Bob", "Bobson", MALE_IMAGE_URL);
    private final User user4 = new User("Bonnie", "Beatty", FEMALE_IMAGE_URL);
    private final User user5 = new User("Chris", "Colston", MALE_IMAGE_URL);
    private final User user6 = new User("Cindy", "Coats", FEMALE_IMAGE_URL);
    private final User user7 = new User("Dan", "Donaldson", MALE_IMAGE_URL);
    private final User user8 = new User("Dee", "Dempsey", FEMALE_IMAGE_URL);
    private final User user9 = new User("Elliott", "Enderson", MALE_IMAGE_URL);
    private final User user10 = new User("Elizabeth", "Engle", FEMALE_IMAGE_URL);
    private final User user11 = new User("Frank", "Frandson", MALE_IMAGE_URL);
    private final User user12 = new User("Fran", "Franklin", FEMALE_IMAGE_URL);
    private final User user13 = new User("Gary", "Gilbert", MALE_IMAGE_URL);
    private final User user14 = new User("Giovanna", "Giles", FEMALE_IMAGE_URL);
    private final User user15 = new User("Henry", "Henderson", MALE_IMAGE_URL);
    private final User user16 = new User("Helen", "Hopwell", FEMALE_IMAGE_URL);
    private final User user17 = new User("Igor", "Isaacson", MALE_IMAGE_URL);
    private final User user18 = new User("Isabel", "Isaacson", FEMALE_IMAGE_URL);
    private final User user19 = new User("Justin", "Jones", MALE_IMAGE_URL);
    private final User user20 = new User("Jill", "Johnson", FEMALE_IMAGE_URL);
    private final User user21 = new User("Andrew", "Bowden", MALE_IMAGE_URL);
    private final User user22 = new User("Ryan", "Bryson", MALE_IMAGE_URL);
    private final User user23 = new User("John", "Simmons", MALE_IMAGE_URL);


    private final Status status1 = new Status(LocalDateTime.parse("2021-02-13T01:01:01"), "message1", null ,null, user22);
    private final Status status2 = new Status(LocalDateTime.parse("2021-02-14T01:01:02"), "@TacoBell , Check out this cool website I found: www.google.com   How's it going @RyanBryson", null, null, user23);
    private final Status status3 = new Status(LocalDateTime.parse("2021-02-15T01:01:03"), "My daughter just learned she is accepted to BYU!\n" +
            "\n" +
            "That's 3 children from my house. \n" +
            "Plus my wife and I met there, \n" +
            "my parents met there, \n" +
            "and my wife's parents met there. ", null ,null, user1);
    private final Status status4 = new Status(LocalDateTime.parse("2021-02-15T01:01:04"), "message4", null, null, user2);
    private final Status status5 = new Status(LocalDateTime.parse("2021-02-15T01:01:05"), "message5", null ,null, user3);
    private final Status status6 = new Status(LocalDateTime.parse("2021-02-15T01:01:06"), "message6", null, null, user4);
    private final Status status7 = new Status(LocalDateTime.parse("2021-02-15T01:01:07"), "message7", null ,null, user5);
    private final Status status8 = new Status(LocalDateTime.parse("2021-02-15T01:01:08"), "message8", null, null, user6);
    private final Status status9 = new Status(LocalDateTime.parse("2021-02-15T01:01:09"), "message9", null ,null, user7);
    private final Status status10 = new Status(LocalDateTime.parse("2021-02-15T01:01:10"), "message10", null, null, user8);
    private final Status status11 = new Status(LocalDateTime.parse("2021-02-15T01:01:11"), "message11", null ,null, user9);
    private final Status status12 = new Status(LocalDateTime.parse("2021-02-15T01:01:12"), "message12", null, null, user10);



    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request. The current implementation
     * returns generated data and doesn't actually make a network request.
     *
     * @param request contains information about the user whose followees are to be returned and any
     *                other information required to satisfy the request.
     * @return the following response.
     */
//    public FollowingResponse getFollowees(FollowingRequest request) {
//
//        // Used in place of assert statements because Android does not support them
//        if(BuildConfig.DEBUG) {
//            if(request.getLimit() < 0) {
//                throw new AssertionError();
//            }
//
//            if(request.getFollowerAlias() == null) {
//                throw new AssertionError();
//            }
//        }
//
//        List<User> allFollowees = getDummyFollowees();
//        List<User> responseFollowees = new ArrayList<>(request.getLimit());
//
//        boolean hasMorePages = false;
//
//        if(request.getLimit() > 0) {
//            int followeesIndex = getFolloweesStartingIndex(request.getLastFolloweeAlias(), allFollowees);
//
//            for(int limitCounter = 0; followeesIndex < allFollowees.size() && limitCounter < request.getLimit(); followeesIndex++, limitCounter++) {
//                responseFollowees.add(allFollowees.get(followeesIndex));
//            }
//
//            hasMorePages = followeesIndex < allFollowees.size();
//        }
//
//        return new FollowingResponse(responseFollowees, hasMorePages);
//    }

    /**
     * Determines the index for the first followee in the specified 'allFollowees' list that should
     * be returned in the current request. This will be the index of the next followee after the
     * specified 'lastFollowee'.
     *
     * @param lastFolloweeAlias the alias of the last followee that was returned in the previous
     *                          request or null if there was no previous request.
     * @param allFollowees the generated list of followees from which we are returning paged results.
     * @return the index of the first followee to be returned.
     */
    private int getFolloweesStartingIndex(String lastFolloweeAlias, List<User> allFollowees) {

        int followeesIndex = 0;

        if(lastFolloweeAlias != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allFollowees.size(); i++) {
                if(lastFolloweeAlias.equals(allFollowees.get(i).getAlias())) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    followeesIndex = i + 1;
                    break;
                }
            }
        }

        return followeesIndex;
    }

    /**
     * Returns the list of dummy followee data. This is written as a separate method to allow
     * mocking of the followees.
     *
     * @return the followees.
     */
    List<User> getDummyFollowees() {
        return Arrays.asList(user1, user2, user3, user4, user5, user6, user7,
                user8, user9, user10, user11, user12, user13, user14, user15, user16, user17, user18,
                user19, user20);
    }

    public FollowersResponse getFollowers(FollowersRequest request) {

        // Used in place of assert statements because Android does not support them
        if(BuildConfig.DEBUG) {
            if(request.getLimit() < 0) {
                throw new AssertionError();
            }

            if(request.getFollowerAlias() == null) {
                throw new AssertionError();
            }
        }

        List<User> allFollowers = getDummyFollowers();
        List<User> responseFollowers = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            int followersIndex = getFollowersStartingIndex(request.getLastFolloweeAlias(), allFollowers);

            for(int limitCounter = 0; followersIndex < allFollowers.size() && limitCounter < request.getLimit(); followersIndex++, limitCounter++) {
                responseFollowers.add(allFollowers.get(followersIndex));
            }

            hasMorePages = followersIndex < allFollowers.size();
        }

        return new FollowersResponse(responseFollowers, hasMorePages);
    }

    /**
     * Determines the index for the first followee in the specified 'allFollowees' list that should
     * be returned in the current request. This will be the index of the next followee after the
     * specified 'lastFollowee'.
     *
     * @param lastFollowerAlias the alias of the last followee that was returned in the previous
     *                          request or null if there was no previous request.
     * @param allFollowers the generated list of followees from which we are returning paged results.
     * @return the index of the first followee to be returned.
     */
    private int getFollowersStartingIndex(String lastFollowerAlias, List<User> allFollowers) {

        int followersIndex = 0;

        if(lastFollowerAlias != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allFollowers.size(); i++) {
                if(lastFollowerAlias.equals(allFollowers.get(i).getAlias())) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    followersIndex = i + 1;
                    break;
                }
            }
        }

        return followersIndex;
    }

    /**
     * Returns the list of dummy followee data. This is written as a separate method to allow
     * mocking of the followees.
     *
     * @return the followees.
     */
    List<User> getDummyFollowers() {
        return Arrays.asList(user2, user4, user6, user7,
                user8, user15, user16, user17, user18,
                user19, user20, user21, user22, user23);
    }

    public StoryResponse getStory(StoryRequest request) {

        // MAJOR TODO: Implement pagination.

        // Used in place of assert statements because Android does not support them
        if(BuildConfig.DEBUG) {
            if(request.getLimit() < 0) {
                throw new AssertionError();
            }

            if(request.getUserAlias() == null) {
                throw new AssertionError();
            }
        }

        List<Status> allStatuses = getDummyStatuses();
        List<Status> responseStatuses = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            int statusIndex = getStatusesStartingIndex(request.getLastTimestamp(), allStatuses);

            for(int limitCounter = 0; statusIndex < allStatuses.size() && limitCounter < request.getLimit(); statusIndex++, limitCounter++) {
                responseStatuses.add(allStatuses.get(statusIndex));
            }

            hasMorePages = statusIndex < allStatuses.size();
        }

        return new StoryResponse(responseStatuses, hasMorePages);
    }

    List<Status> getDummyStatuses() {
        return Arrays.asList(status1, status2, status3, status4, status5, status6, status7,
                status8, status9, status10, status11, status12);
    }


    private int getStatusesStartingIndex(LocalDateTime lastTimeStamp, List<Status> allStatuses) {

        int statusIndex = 0;

        if(lastTimeStamp != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allStatuses.size(); i++) {
                if(lastTimeStamp.equals(allStatuses.get(i).getTimestamp())) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    statusIndex = i + 1;
                    break;
                }
            }
        }

        return statusIndex;
    }

    List<User> getDummyUsers() {
        return Arrays.asList(user1, user2, user3, user4, user5, user6, user7,
                user8, user9, user10, user11, user12, user13, user14, user15, user16, user17, user18,
                user19, user20, user21, user22, user23);
    }

    public GetUserResponse getUser(GetUserRequest request) {
        List<User> allUsers = getDummyUsers();

        for (User user : allUsers) {
            if (user.getAlias().equals(request.getAlias())) {
                return new GetUserResponse(user);
            }
        }
        return new GetUserResponse(null);
    }

//    public FollowResponse setFollow(FollowRequest request) {
//        return new FollowResponse(true, request.isFollowRequest());
//    }
//
//    public FollowResponse setUnfollow(FollowRequest request) {
//        return new FollowResponse(true, request.isFollowRequest());
//    }

//    public UserFollowCountResponse getUserFollowCount(UserFollowCountRequest request) {
//        return new UserFollowCountResponse(100, 99);
//    }
//
//    public PostStatusResponse sendStatus(PostStatusRequest postStatusRequest) {
//        return new PostStatusResponse(status1);
//    }

//    public FollowStatusResponse getFollowStatus(FollowStatusRequest request) {
//        return new FollowStatusResponse(true, true);
//    }

    public FeedResponse getFeed(FeedRequest request) {
        // Used in place of assert statements because Android does not support them
        if(BuildConfig.DEBUG) {
            if(request.getLimit() < 0) {
                throw new AssertionError();
            }

            if(request.getAuthToken() == null) {
                throw new AssertionError();
            }
        }

        List<Status> allStatuses = getDummyStatuses();
        List<Status> responseStatuses = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            int statusIndex = getStatusesStartingIndex(request.getLastTimestamp(), allStatuses);

            for(int limitCounter = 0; statusIndex < allStatuses.size() && limitCounter < request.getLimit(); statusIndex++, limitCounter++) {
                responseStatuses.add(allStatuses.get(statusIndex));
            }

            hasMorePages = statusIndex < allStatuses.size();
        }

        return new FeedResponse(responseStatuses, hasMorePages);
    }

}
