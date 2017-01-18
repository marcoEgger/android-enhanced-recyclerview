package de.marco_egger.enhancedrecyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import de.marco_egger.enhanced_recyclerview.EnhancedViewHolder;
import de.marco_egger.enhancedrecyclerview.model.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marco Egger
 */
public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {

    @NonNull
    private List<Task> tasks;

    public TasksAdapter() {
        this.tasks = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setItem(tasks.get(position));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    /**
     * Add a task with animation.
     *
     * @param task the task to add
     */
    public void addTask(Task task) {
        tasks.add(task);
        notifyItemInserted(tasks.size());
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
