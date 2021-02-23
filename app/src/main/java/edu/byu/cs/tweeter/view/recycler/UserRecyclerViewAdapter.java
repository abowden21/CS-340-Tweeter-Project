package edu.byu.cs.tweeter.view.recycler;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import edu.byu.cs.tweeter.model.domain.User;

public class UserRecyclerViewAdapter<T_Holder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected final List<User> users = new ArrayList<>();

    protected boolean hasMorePages;
    protected boolean isLoading = false;

    private int LOADING_DATA_VIEW;
    private int ITEM_VIEW;

    public UserRecyclerViewAdapter(int loadingDataView, int itemView) {
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

    protected void addItems(List<User> newUsers) {
        int startInsertPosition = users.size();
        users.addAll(newUsers);
        this.notifyItemRangeInserted(startInsertPosition, newUsers.size());
    }

    private void addItem(User user) {
        users.add(user);
        this.notifyItemInserted(users.size() - 1);
    }

    private void removeItem(User user) {
        int position = users.indexOf(user);
        users.remove(position);
        this.notifyItemRemoved(position);
    }

    public void handleException(Exception exception) {
        removeLoadingFooter();
    }

    protected void loadMoreItems() {
        isLoading = true;
        addLoadingFooter();
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == users.size() - 1 && isLoading) ? LOADING_DATA_VIEW : ITEM_VIEW;
    }

    protected void addLoadingFooter() {
        addItem(new User("Dummy", "User", ""));
    }

    protected void removeLoadingFooter() {
        removeItem(users.get(users.size() - 1));
    }

    public boolean getIsLoading() {
        return this.isLoading;
    }

    public boolean getHasMorePages() {
        return this.hasMorePages;
    }

}