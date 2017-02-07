package ebozkurt.listtodo;

import android.content.Context;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class TaskLab {

    private static TaskLab sTaskLab;
    private List<Task> mTasks;

    public static TaskLab get(Context context) {
        if (sTaskLab == null) {
            sTaskLab = new TaskLab(context);
        }
        return sTaskLab;
    }

    private TaskLab(Context context) {
        mTasks = new ArrayList<>();
/*
        for (int i = 0; i < 20; i++) {
            Task task = new Task();
            task.setTitle("Task #" + i);
            task.setDescription("Task description #" + i);
            task.setDone(i % 2 == 0); //every other task will be done
            task.setPriority(i % 4);
            mTasks.add(task);
        }
*/
        Task task = new Task();
        task.setTitle("Task 1");
        task.setDescription("Task description 1");
        task.setDone(true);
        task.setPriority(1);
        task.setChildTasks(null);
        task.setParentTask(null);
        mTasks.add(task);

        Task c1 = new Task();
        c1.setTitle("child 1");
        c1.setDescription("c1");
        c1.setDone(true);
        c1.setPriority(1);
        c1.setParentTask(task);
        task.addChildTask(c1);
        mTasks.add(c1);




    }

    public void addTask(Task t) {
        mTasks.add(t);
    }

    public void removeTask(Task t) {
        mTasks.remove(t);
    }

    public List<Task> getTasks() {
        return mTasks;
    }

    public Task getTask(UUID id) {
        for (Task task : mTasks) {
            if (task.getId().equals(id)) {
                return task;
            }
        }
        return null;
    }

}
