package edu.byu.cs.tweeter.view.main;

import android.os.Build;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StatusRecyclerViewAdapter<T_Holder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected final List<Status> statuses = new ArrayList<>();

    protected boolean hasMorePages;
    protected boolean isLoading = false;

    private int LOADING_DATA_VIEW;
    private int ITEM_VIEW;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public StatusRecyclerViewAdapter(int loadingDataView, int itemView) {
        this.LOADING_DATA_VIEW = loadingDataView;
        this.ITEM_VIEW = itemView;
        loadMoreItems();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    protected void addItems(List<Status> newStatuses) {
        int startInsertPosition = statuses.size();
        statuses.addAll(newStatuses);
        this.notifyItemRangeInserted(startInsertPosition, newStatuses.size());
    }

    private void addItem(Status status) {
        statuses.add(status);
        this.notifyItemInserted(statuses.size() - 1);
    }

    private void removeItem(Status status) {
        int position = statuses.indexOf(status);
        statuses.remove(position);
        this.notifyItemRemoved(position);
    }

    public void handleException(Exception exception) {
        removeLoadingFooter();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void loadMoreItems() {
        isLoading = true;
        addLoadingFooter();
    }

    @Override
    public int getItemCount() {
        return statuses.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == statuses.size() - 1 && isLoading) ? LOADING_DATA_VIEW : ITEM_VIEW;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void addLoadingFooter() {
        User u = new User("Dummy", "User", "");
        addItem(new Status(LocalDateTime.now(), "", new ArrayList<String>(), new ArrayList<String>(), u));
    }

    protected void removeLoadingFooter() {
        removeItem(statuses.get(statuses.size() - 1));
    }

    public boolean getIsLoading() {
        return this.isLoading;
    }

    public boolean getHasMorePages() {
        return this.hasMorePages;
    }
}
