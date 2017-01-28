package de.marco_egger.enhancedrecyclerview;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import de.marco_egger.enhanced_recyclerview.EnhancedRecyclerViewAdapter;
import de.marco_egger.enhanced_recyclerview.EnhancedViewHolder;
import de.marco_egger.enhancedrecyclerview.model.Task;

/**
 * @author Marco Egger
 */
public class TasksAdapter extends EnhancedRecyclerViewAdapter<Task, TasksAdapter.ViewHolder> {

    @Override
    protected int getItemLayout(int viewType) {
        return android.R.layout.simple_list_item_1;
    }

    @NonNull
    @Override
    protected ViewHolder createNewViewHolder(@NonNull View v, int viewType) {
        return new ViewHolder(v);
    }

    static class ViewHolder extends EnhancedViewHolder<Task> {

        private final TextView textView;

        ViewHolder(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(android.R.id.text1);
        }

        @Override
        public void setItem(@NonNull Task item) {
            super.setItem(item);

            textView.setText(item.getName());
        }
    }
}
