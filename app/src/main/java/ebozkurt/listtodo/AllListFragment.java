package ebozkurt.listtodo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ebozkurt.listtodo.RecyclerView_Helper.ItemTouchHelperAdapter;
import ebozkurt.listtodo.RecyclerView_Helper.ItemTouchHelperViewHolder;
import ebozkurt.listtodo.RecyclerView_Helper.SimpleItemTouchHelperCallback;


public class AllListFragment extends Fragment {

    private static final String TAG = "AllListFragment";

    private RecyclerView mTaskRecyclerView;
    private TaskAdapter mAdapter;

    //private FloatingActionButton mAddTaskFloatingActionButton;

    /* public static AllListFragment newInstance(){
         return new AllListFragment();
     }
 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_tasks_list, container, false);
        mTaskRecyclerView = (RecyclerView) view
                .findViewById(R.id.fragment_all_tasks_list_recycler_view);
        mTaskRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mTaskRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getResources()));


        final FloatingActionButton mAddTaskFloatingActionButton = (FloatingActionButton) view.findViewById(R.id.fragment_all_tasks_list_add_task_floating_action_button);
        mAddTaskFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "FAB clicked, adding a new task");
                addNewTask();
            }
        });

        updateUI();

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mTaskRecyclerView);

        mTaskRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 || dy < 0 && mAddTaskFloatingActionButton.isShown())
                    mAddTaskFloatingActionButton.hide();
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mAddTaskFloatingActionButton.show();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });


        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        TaskLab taskLab = TaskLab.get(getActivity());
        List<Task> tasks = taskLab.getTasks();

        if (mAdapter == null) {
            mAdapter = new TaskAdapter(tasks);
            mTaskRecyclerView.setAdapter(mAdapter);

        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    private void addNewTask() {
        Task task = new Task();
        TaskLab.get(getActivity()).addTask(task);
        Intent intent = TaskActivity.newIntent(getActivity(), task.getId());
        startActivity(intent);
    }


    private class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ItemTouchHelperViewHolder {
        private Task mTask;
        private TextView mTitleTextView;
        private TextView mDescriptionTextView;
        private CheckBox mDoneCheckBox;
        private View mPriorityView;
        //private FloatingActionButton mFloatingActionButton;

        public TaskHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_task_title_text_view);
            mDescriptionTextView = (TextView) itemView.findViewById(R.id.list_item_task_description_text_view);
            mDoneCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_task_done_check_box);
            mPriorityView = (View) itemView.findViewById(R.id.list_item_task_priority_view);
        }

        public void bindTask(Task task) {
            mTask = task;
            mTitleTextView.setText(mTask.getTitle());
            mDescriptionTextView.setText(mTask.getDescription());
            mDoneCheckBox.setChecked(mTask.isDone());
            setPriorityColor(mTask);
            //mPriorityView.setBackgroundColor(244);
        }


        public void setPriorityColor(Task task) {
            mTask = task;
            int i = mTask.getPriority();
            int s = 0;
            switch (i) {
                case 0:
                    s = ContextCompat.getColor(getContext(), R.color.priority0);
                    break;
                case 1:
                    s = ContextCompat.getColor(getContext(), R.color.priority1);
                    break;
                case 2:
                    s = ContextCompat.getColor(getContext(), R.color.priority2);
                    break;
                case 3:
                    s = ContextCompat.getColor(getContext(), R.color.priority3);
                    break;
            }
            mPriorityView.setBackgroundColor(s);

        }


        @Override
        public void onClick(View v) {
            Log.i(TAG, mTask.getTitle() + " clicked");

            /*
            Log.i(TAG, mTask.getParentTask().toString());
            Log.i(TAG, mTask.getChildTasks().toString());
            //todo if you want to keep logs for parent/child tasks than use exception handling
*/

            Intent intent = TaskActivity.newIntent(getActivity(), mTask.getId());
            startActivity(intent);

        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
            Log.i(TAG, "onItemSelected: ");
        }

        @Override
        public void onItemClear() {
            Log.i(TAG, "onItemClear: ");
            itemView.setBackgroundColor(0);
            /*
            mTaskRecyclerView.invalidate();
            updateUI();*/
            // mAdapter.notifyDataSetChanged();
        }


    }


    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder> implements ItemTouchHelperAdapter {
        private List<Task> mTasks;

        public TaskAdapter(List<Task> tasks) {
            mTasks = tasks;
        }


        @Override
        public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(R.layout.list_item_task, parent, false);
            return new TaskHolder(view);
        }

        @Override
        public void onBindViewHolder(TaskHolder holder, int position) {
            final Task task = mTasks.get(position);
            holder.bindTask(task);


            holder.mDoneCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (compoundButton.isPressed())//used for checking if checkbox is accually pressed, to prevent random checkbox toggles after recyclerview is scrolled
                    {
                        Log.i(TAG, task.getTitle() + " checkbox changed to " + b);
                        task.setDone(b);
                    }
                }
            });
        }

        @Override
        public void onItemMove(int fromPosition, int toPosition) {

            Task prev = mTasks.remove(fromPosition);
            mTasks.add(toPosition, prev);
            Log.i(TAG, "onItemMove: " + fromPosition + " " + toPosition);
            notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public void onItemDismiss(final int position) {
            Log.i(TAG, "onItemDismiss: " + position);
            final Task deleted = mTasks.remove(position);
            mAdapter.notifyDataSetChanged();
            Snackbar.make(mTaskRecyclerView, R.string.task_deleted, Snackbar.LENGTH_LONG)
                    .setAction(R.string.undo, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mTasks.add(position, deleted);
                            notifyDataSetChanged();
                            Log.i(TAG, "Task " + deleted.getTitle() + " is restored to position " + position);
                        }
                    }).show();
        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }

    }


}
