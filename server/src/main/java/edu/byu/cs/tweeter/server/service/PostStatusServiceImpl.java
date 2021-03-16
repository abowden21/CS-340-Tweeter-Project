package edu.byu.cs.tweeter.server.service;
import java.time.LocalDateTime;

import edu.byu.cs.tweeter.server.dao.StatusDAO;
import edu.byu.cs.tweeter.shared.model.domain.Status;
import edu.byu.cs.tweeter.shared.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.model.request.PostStatusRequest;
import edu.byu.cs.tweeter.shared.model.response.PostStatusResponse;
import edu.byu.cs.tweeter.shared.model.service.PostStatusServiceInterface;

public class PostStatusServiceImpl implements PostStatusServiceInterface {
    StatusDAO statusDao;

    @Override
    public PostStatusResponse sendStatus(PostStatusRequest postStatusRequest) {
        LocalDateTime timestamp = LocalDateTime.now();
        Status status = new Status(timestamp, postStatusRequest.getMessage(), null);
        getStatusDao().addStatus(status);
        PostStatusResponse response = new PostStatusResponse(status);
        return response;
    }


    public StatusDAO getStatusDao() {
        if (this.statusDao == null)
            this.statusDao = new StatusDAO();
        return this.statusDao;
    }
}
