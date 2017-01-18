package de.marco_egger.enhanced_recyclerview;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marco Egger
 */
public abstract class EnhancedRecyclerViewAdapter<D, H extends EnhancedViewHolder<D>> extends RecyclerView.Adapter<H> {

    @NonNull
    protected List<D> items;

    public EnhancedRecyclerViewAdapter() {
        this.items = new ArrayList<>();
    }

    @Override
    public H onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(getItemLayout(), parent, false);
        return createNewViewHolder(v);
    }

    @Override
    public void onBindViewHolder(H holder, int position) {
        holder.setItem(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void replaceItemsNotified(@NonNull List<D> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void addItemNotified(@NonNull D item) {
        items.add(item);
        notifyItemInserted(items.size());
    }

    public void clearNotified() {
        int size = items.size();
        items.clear();
        notifyItemRangeRemoved(0, size);
    }

    @LayoutRes
    protected abstract int getItemLayout();

    @NonNull
    protected abstract H createNewViewHolder(@NonNull View v);
}
