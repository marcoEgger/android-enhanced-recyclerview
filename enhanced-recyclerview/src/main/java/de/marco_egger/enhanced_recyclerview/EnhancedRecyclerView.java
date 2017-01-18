package de.marco_egger.enhanced_recyclerview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Enhanced {@link RecyclerView} that provides methods for an empty or a loading view. An empty view will be
 * shown automatically (when set) if there is no adapter set or no items retrieved by the adapter. The loading view
 * can be displayed with {@link #setLoading(boolean)}. If set to loading, the adapter's data set can be empty,
 * with the empty view not being shown.
 *
 * @author Marco Egger
 * @see RecyclerView
 */
public class EnhancedRecyclerView extends RecyclerView {

    private static final int ANIMATION_TIME = 200;

    private static final int VIEW_STATE_LIST = 1;
    private static final int VIEW_STATE_LOADING = 2;
    private static final int VIEW_STATE_EMPTY = 3;

    // UI references
    @Nullable
    private View loadingView;
    @Nullable
    private View emptyView;

    // Some state variables
    private boolean loading = false;
    // Observer for data-set changes
    final private AdapterDataObserver observer = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            updateUi();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            updateUi();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            updateUi();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            updateUi();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            updateUi();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            updateUi();
        }
    };

    public EnhancedRecyclerView(Context context) {
        super(context);
    }

    public EnhancedRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EnhancedRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        // First unregister the observer from an possible previous adapter
        final Adapter oldAdapter = getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(observer);
        }

        // Call super and register observer on new adapter
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(observer);
        }

        // Finally update the UI
        updateUi();
    }

    private void updateUi() {
        if (loading) {
            showViewState(VIEW_STATE_LOADING);
        } else if (isEmpty()) {
            showViewState(VIEW_STATE_EMPTY);
        } else {
            showViewState(VIEW_STATE_LIST);
        }
    }

    /**
     * Updates the view state accordingly.
     *
     * @param viewState the state which should be displayed
     * @see #VIEW_STATE_EMPTY
     * @see #VIEW_STATE_LOADING
     * @see #VIEW_STATE_LIST
     */
    private void showViewState(int viewState) {
        switch (viewState) {
            case VIEW_STATE_EMPTY:
                showListView(false);
                showLoadingView(false);
                showEmptyView(true);
                break;

            case VIEW_STATE_LOADING:
                showListView(false);
                showLoadingView(true);
                showEmptyView(false);
                break;

            case VIEW_STATE_LIST:
                showListView(true);
                showLoadingView(false);
                showEmptyView(false);
                break;
        }
    }

    private void showListView(final boolean show) {
        setVisibility(show ? VISIBLE : GONE);
        // Only animate if we're not in the edit mode (in a developer tool)
        if (!isInEditMode()) {
            animate().setDuration(ANIMATION_TIME).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    setVisibility(show ? VISIBLE : GONE);
                }
            });
        }
    }

    private void showLoadingView(final boolean show) {
        if (loadingView != null) {
            loadingView.setVisibility(show ? View.VISIBLE : View.GONE);
            loadingView.animate().setDuration(ANIMATION_TIME).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    loadingView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        }
    }

    /**
     * @param show {@code true} if the empty view should be displayed, otherwise {@code false}
     */
    private void showEmptyView(final boolean show) {
        if (emptyView != null) {
            emptyView.setVisibility(show ? View.VISIBLE : View.GONE);
            emptyView.animate().setDuration(ANIMATION_TIME).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    emptyView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        }
    }

    /**
     * @param loadingView set the view that should be displayed as long as loading is set to {@code true}
     * @see #setLoading(boolean)
     */
    public void setLoadingView(View loadingView) {
        this.loadingView = loadingView;
        updateUi();
    }

    /**
     * @param emptyView the view that should be displayed if the data set is empty and the list is not set to loading
     * @see #setLoading(boolean)
     */
    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
        updateUi();
    }

    /**
     * @param loading {@code true} if loading view should be displayed, otherwise {@code false}
     * @see #setLoadingView(View)
     */
    public void setLoading(boolean loading) {
        this.loading = loading;
        updateUi();
    }

    /**
     * @return {@code true} if adapter's data set is empty or no adapter is available, otherwise {@code false}
     */
    private boolean isEmpty() {
        return getAdapter() == null || getAdapter().getItemCount() == 0;
    }
}
