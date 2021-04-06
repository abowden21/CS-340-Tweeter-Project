package edu.byu.cs.tweeter.server.dao;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;

import edu.byu.cs.tweeter.server.DataAccessException;
import edu.byu.cs.tweeter.shared.model.domain.AuthToken;

public class AuthTokenDAO extends BaseDynamoDAO {

    private final String tokenAttr = "token";
    private final String aliasAttr = "userAlias";
    private final String expirationAttr = "expiration";

    public AuthTokenDAO() {
        super("authtoken");
    }

    public void addAuthToken(AuthToken authToken) throws DataAccessException {
        Item item = new Item().withString(tokenAttr, authToken.getToken())
                .withString(aliasAttr, authToken.getUserAlias())
                .withString(expirationAttr, authToken.getExpiration());
        PutItemOutcome putItemOutcome = getTable().putItem(item);
//        putItemOutcome.getPutItemResult().
        // TODO: figure out if it worked
    }

    public AuthToken getAuthToken(String tokenValue) throws DataAccessException {

        Item item = getTable().getItem("token", tokenValue);
        if (item == null) {
            throw new DataAccessException("No access token exists for " + tokenValue);
        }
//        AuthToken returnToken = new AuthToken(
//                item.getString(tokenAttr),
//                item.getString(aliasAttr),
//                item.getString(expirationAttr));
       // return returnToken;
        //TODO Fix This
        return null;
    }
    public void updateAuthToken(String token, AuthToken authToken) throws DataAccessException {}

    public void deleteAuthToken(String token) throws DataAccessException {}
}
