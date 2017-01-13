package ebozkurt.listtodo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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


import java.util.List;

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
        //Todo later try staggered grid layout manager!!



        FloatingActionButton mAddTaskFloatingActionButton = (FloatingActionButton) view.findViewById(R.id.fragment_all_tasks_list_add_task_floating_action_button);
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

    private void removeTask(Task task) {
        //todo remove this if not required later
        //Task t = task;
        TaskLab.get(getActivity()).removeTask(task);
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
            String s = " ";
            switch (i) {
                case 0:
                    s = "#ffbf00";
                    break;
                case 1:
                    s = "#ff8000";
                    break;
                case 2:
                    s = "#ff4000";
                    break;
                case 3:
                    s = "#ff0000";
                    break;
            }
            mPriorityView.setBackgroundColor(Color.parseColor(s));

        }


        @Override
        public void onClick(View v) {
            Log.i(TAG, mTask.getTitle() + " clicked");
            String s = mTask.getParentTask().getTitle();
            Log.i(TAG, "parent task: " + s);
/*
            Intent intent = TaskActivity.newIntent(getActivity(), mTask.getId());
            startActivity(intent);
*/


/*
            removeTask(mTask);
            mAdapter.notifyDataSetChanged();
            mTaskRecyclerView.invalidate();
            Log.i(TAG, "invalidation");
            updateUI();
*/
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
            Log.i(TAG, "onItemSelected: " );
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
        public void onItemDismiss(int position) {
            Log.i(TAG, "onItemDismiss: " + position);
            mTasks.remove(position);
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }

    }


}
