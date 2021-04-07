package edu.byu.cs.tweeter.server.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.byu.cs.tweeter.shared.model.domain.User;
import edu.byu.cs.tweeter.shared.model.request.FollowersRequest;
import edu.byu.cs.tweeter.shared.model.response.FollowersResponse;

public class FollowersDAO {
//    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
//    private static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";
//
//    private final User user1 = new User("Allen", "Anderson", MALE_IMAGE_URL);
//    private final User user2 = new User("Amy", "Ames", FEMALE_IMAGE_URL);
//    private final User user3 = new User("Bob", "Bobson", MALE_IMAGE_URL);
//    private final User user4 = new User("Bonnie", "Beatty", FEMALE_IMAGE_URL);
//    private final User user5 = new User("Chris", "Colston", MALE_IMAGE_URL);
//    private final User user6 = new User("Cindy", "Coats", FEMALE_IMAGE_URL);
//    private final User user7 = new User("Dan", "Donaldson", MALE_IMAGE_URL);
//    private final User user8 = new User("Dee", "Dempsey", FEMALE_IMAGE_URL);
//    private final User user9 = new User("Elliott", "Enderson", MALE_IMAGE_URL);
//    private final User user10 = new User("Elizabeth", "Engle", FEMALE_IMAGE_URL);
//    private final User user11 = new User("Frank", "Frandson", MALE_IMAGE_URL);
//    private final User user12 = new User("Fran", "Franklin", FEMALE_IMAGE_URL);
//    private final User user13 = new User("Gary", "Gilbert", MALE_IMAGE_URL);
//    private final User user14 = new User("Giovanna", "Giles", FEMALE_IMAGE_URL);
//    private final User user15 = new User("Henry", "Henderson", MALE_IMAGE_URL);
//    private final User user16 = new User("Helen", "Hopwell", FEMALE_IMAGE_URL);
//    private final User user17 = new User("Igor", "Isaacson", MALE_IMAGE_URL);
//    private final User user18 = new User("Isabel", "Isaacson", FEMALE_IMAGE_URL);
//    private final User user19 = new User("Justin", "Jones", MALE_IMAGE_URL);
//    private final User user20 = new User("Jill", "Johnson", FEMALE_IMAGE_URL);
//
//    public Integer getFollowerCount(User follower) {
//        // TODO: uses the dummy data.  Replace with a real implementation.
//        assert follower != null;
//        return getDummyFollowers().size();
//    }
//
//    public FollowersResponse getFollowers(FollowersRequest request) {
//
//        // Used in place of assert statements because Android does not support them
//        assert request.getLimit() > 0;
//        assert request.getFollowerAlias() != null;
//
//        List<User> allFollowers = getDummyFollowers();
//        List<User> responseFollowers = new ArrayList<>(request.getLimit());
//
//        boolean hasMorePages = false;
//
//        if(request.getLimit() > 0) {
//            int followersIndex = getFollowersStartingIndex(request.getLastFolloweeAlias(), allFollowers);
//
//            for(int limitCounter = 0; followersIndex < allFollowers.size() && limitCounter < request.getLimit(); followersIndex++, limitCounter++) {
//                responseFollowers.add(allFollowers.get(followersIndex));
//            }
//
//            hasMorePages = followersIndex < allFollowers.size();
//        }
//
//        return new FollowersResponse(responseFollowers, hasMorePages);
//    }
//
//    /**
//     * Determines the index for the first followee in the specified 'allFollowees' list that should
//     * be returned in the current request. This will be the index of the next followee after the
//     * specified 'lastFollowee'.
//     *
//     * @param lastFollowerAlias the alias of the last followee that was returned in the previous
//     *                          request or null if there was no previous request.
//     * @param allFollowers the generated list of followees from which we are returning paged results.
//     * @return the index of the first followee to be returned.
//     */
//    private int getFollowersStartingIndex(String lastFollowerAlias, List<User> allFollowers) {
//
//        int followersIndex = 0;
//
//        if(lastFollowerAlias != null) {
//            // This is a paged request for something after the first page. Find the first item
//            // we should return
//            for (int i = 0; i < allFollowers.size(); i++) {
//                if(lastFollowerAlias.equals(allFollowers.get(i).getAlias())) {
//                    // We found the index of the last item returned last time. Increment to get
//                    // to the first one we should return
//                    followersIndex = i + 1;
//                    break;
//                }
//            }
//        }
//
//        return followersIndex;
//    }
//
//    /**
//     * Returns the list of dummy followee data. This is written as a separate method to allow
//     * mocking of the followees.
//     *
//     * @return the followees.
//     */
//    List<User> getDummyFollowers() {
//        return Arrays.asList(user2, user4, user6, user7,
//                user8, user15, user16, user17, user18,
//                user19, user20, user1);
//    }
}
