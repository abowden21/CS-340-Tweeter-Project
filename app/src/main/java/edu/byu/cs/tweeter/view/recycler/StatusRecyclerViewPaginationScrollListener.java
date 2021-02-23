package edu.byu.cs.tweeter.view.recycler;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class StatusRecyclerViewPaginationScrollListener extends RecyclerView.OnScrollListener {

    private final StatusRecyclerViewAdapter adapter;
    private final LinearLayoutManager layoutManager;

    /**
     * Creates a new instance.
     *
     * @param layoutManager the layout manager being used by the RecyclerView.
     */
    public StatusRecyclerViewPaginationScrollListener(LinearLayoutManager layoutManager, StatusRecyclerViewAdapter adapter) {
        this.layoutManager = layoutManager;
        this.adapter = adapter;
    }

    /**
     * Determines whether the user has scrolled to the bottom of the currently available data
     * in the RecyclerView and asks the adapter to load more data if the last load request
     * indicated that there was more data to load.
     *
     * @param recyclerView the RecyclerView.
     * @param dx the amount of horizontal scroll.
     * @param dy the amount of vertical scroll.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

        if (!adapter.getIsLoading() && adapter.getHasMorePages()) {
            if ((visibleItemCount + firstVisibleItemPosition) >=
                    totalItemCount && firstVisibleItemPosition >= 0) {
                adapter.loadMoreItems();
            }
        }
    }
}