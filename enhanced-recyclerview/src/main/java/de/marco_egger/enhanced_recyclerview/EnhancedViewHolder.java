package de.marco_egger.enhanced_recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author Marco Egger
 */
public class EnhancedViewHolder<T> extends RecyclerView.ViewHolder {

    protected T item;

    public EnhancedViewHolder(View itemView) {
        super(itemView);
    }

    public void setItem(@NonNull T item) {
        this.item = item;
    }
}
