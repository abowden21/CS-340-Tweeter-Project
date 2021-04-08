package edu.byu.cs.tweeter.shared.model.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.byu.cs.tweeter.shared.model.domain.Status;

public class StoryResponse extends PagedResponse {

    private List<Status> statuses;

    public StoryResponse() {
        statuses = new ArrayList<>();
        setSuccess(true);
    }
    public StoryResponse(List<Status> statuses) {
        this.statuses = statuses;
        setSuccess(true);
    }

    /**
     * Creates a response indicating that the corresponding request was unsuccessful. Sets the
     * success and more pages indicators to false.
     *
     * @param message a message describing why the request was unsuccessful.
     */
    public StoryResponse(String message) {
        super(false, message, false);
    }

    /**
     * Creates a response indicating that the corresponding request was successful.
     *
     * @param statuses the statuses to be included in the result.
     * @param hasMorePages an indicator of whether more data is available for the request.
     */
    public StoryResponse(List<Status> statuses, boolean hasMorePages) {
        super(true, hasMorePages);
        this.statuses = statuses;
    }

    public List<Status> getStatuses() {
        return statuses;
    }
    public void setStatuses(List<Status> statuses) {
        this.statuses = statuses;
    }

    @Override
    public boolean equals(Object param) {
        if (this == param) {
            return true;
        }

        if (param == null || getClass() != param.getClass()) {
            return false;
        }

        StoryResponse that = (StoryResponse) param;

        return (Objects.equals(statuses, that.statuses) &&
                Objects.equals(this.getMessage(), that.getMessage()) &&
                this.isSuccess() == that.isSuccess());
    }

    @Override
    public int hashCode() {
        return Objects.hash(statuses);
    }

}
