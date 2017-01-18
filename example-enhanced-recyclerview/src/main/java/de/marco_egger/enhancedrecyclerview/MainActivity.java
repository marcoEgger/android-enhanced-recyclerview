package de.marco_egger.enhancedrecyclerview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import de.marco_egger.enhanced_recyclerview.EnhancedRecyclerView;
import de.marco_egger.enhancedrecyclerview.model.Task;

public class MainActivity extends AppCompatActivity {

    private TasksAdapter adapter;

    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View emptyView = findViewById(R.id.empty_view);
        View loadingView = findViewById(R.id.loading_view);
        final EnhancedRecyclerView list = (EnhancedRecyclerView) findViewById(R.id.list);

        list.setEmptyView(emptyView);
        list.setLoadingView(loadingView);

        adapter = new TasksAdapter();
        list.setAdapter(adapter);

        final Handler handler = new Handler();

        // Define a new thread to simulate loading
        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                for (int i = 1; i <= 20; i++) {
                    adapter.addTask(new Task("Task No. " + i));
                }

                // Add a little time delay to show the loading view
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    return;
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        list.setLoading(false);
                    }
                });


            }
        });

        // Start loading with a delay so the empty view can be shown
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                list.setLoading(true);
                thread.start();
            }
        }, 2000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (thread != null) {
            thread.interrupt();
        }
    }
}
