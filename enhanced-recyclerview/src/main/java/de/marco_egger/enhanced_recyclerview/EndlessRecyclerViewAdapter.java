package de.marco_egger.enhanced_recyclerview;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This adapter provides an easy to use endless list feature for any {@link RecyclerView}.
 * Just use your usual adapter and pass it to this class' constructor. Setup the {@link EndlessScrollEventListener}
 * to listen for loading events. Do <b>NOT</b> use 9999 as return value for
 * {@link android.support.v7.widget.RecyclerView.Adapter#getItemViewType(int)}!
 *
 * @author Marco Egger
 */
public class EndlessRecyclerViewAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_LOADING = 9999;

    @NonNull
    private final RecyclerView.Adapter<RecyclerView.ViewHolder> wrappedAdapter;
    private final int loadingViewResId;

    @NonNull
    private AtomicBoolean loadingEnabled;
    @NonNull
    private AtomicBoolean dataPending;

    @NonNull
    private final EndlessScrollEventListener listener;

    public EndlessRecyclerViewAdapter(@NonNull RecyclerView.Adapter<RecyclerView.ViewHolder> wrappedAdapter,
                                      @LayoutRes int loadingViewResId,
                                      @NonNull EndlessScrollEventListener endlessScrollEventListener) {
        // Store some references
        this.wrappedAdapter = wrappedAdapter;
        this.loadingViewResId = loadingViewResId;
        this.listener = endlessScrollEventListener;

        // Initialize adapter state
        this.loadingEnabled = new AtomicBoolean(true);
        this.dataPending = new AtomicBoolean(false);

        // Register data observer to avoid inconsistency between this and the wrapped adapter
        this.wrappedAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                notifyItemRangeChanged(positionStart, itemCount);
            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
                notifyItemRangeChanged(positionStart, itemCount, payload);
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                notifyItemRangeInserted(positionStart, itemCount);
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                notifyItemRangeRemoved(positionStart, itemCount);
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Return the loading view if viewType matches
        if (viewType == VIEW_TYPE_LOADING) {
            return new LoadingViewHolder(getPendingView(parent));
        } else {
            return wrappedAdapter.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // Check if the view should be bound to the loading view
        if (holder instanceof LoadingViewHolder) {
            // Only request new data if there is no data loading process running and loading is enabled
            if (!dataPending.get() && loadingEnabled.get()) {
                dataPending.set(true);
                listener.onLoadMoreRequested();
            }
        } else {
            wrappedAdapter.onBindViewHolder(holder, position);
        }
    }

    @Override
    public int getItemCount() {
        // Add one item count if loading is enabled
        return wrappedAdapter.getItemCount() + (loadingEnabled.get() ? 1 : 0);
    }

    @Override
    public int getItemViewType(int position) {
        // Check if we are at the end of the list
        if (position == wrappedAdapter.getItemCount()) {
            return VIEW_TYPE_LOADING;
        }

        return super.getItemViewType(position);
    }

    /**
     * Notify the adapter about the loaded data.
     */
    public void onDataReady() {
        dataPending.set(false);
    }

    /**
     * Stop the adapter from showing the loading view and calling the listeners method to load more data.
     */
    public void stopLoading() {
        dataPending.set(false);
        setLoadingEnabled(false);
    }

    /**
     * Restart the loading process, so the {@link EndlessScrollEventListener#onLoadMoreRequested()}
     * is called again on scrolling down.
     */
    public void restartLoading() {
        dataPending.set(false);
        setLoadingEnabled(true);
    }

    /**
     * Enable or disable the endless loading process.
     *
     * @param newValue {@code true} for enabling the process, {@code false} for disabling
     */
    private void setLoadingEnabled(boolean newValue) {
        // Store new value
        loadingEnabled.set(newValue);

        // Notify about the addition/removal of the loading view
        if (loadingEnabled.get()) {
            wrappedAdapter.notifyItemInserted(getItemCount());
        } else {
            wrappedAdapter.notifyItemRemoved(getItemCount());
        }
    }

    /**
     * The an inflated {@link View} of the loading view.
     *
     * @param viewGroup the parent of the inflated view
     * @return the inflated view
     */
    private View getPendingView(ViewGroup viewGroup) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(loadingViewResId, viewGroup, false);
    }

    public interface EndlessScrollEventListener {

        /**
         * The adapter requests to load more data.
         */
        void onLoadMoreRequested();
    }

    private static class LoadingViewHolder extends RecyclerView.ViewHolder {

        public LoadingViewHolder(View itemView) {
            super(itemView);
        }
    }
}
