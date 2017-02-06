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
        for (int i = 0 ; i < 5 ; i++) {
            String a = Integer.toString(i);
            mTasks.add(createTask(a, a, true, 1, null, null));
        } */
        Task parent = createTask("1", "1", true, 1, null, null);
        Task c1 =createTask("2", "2", true, 1, parent, null);
        Task c2 = createTask("3","3",true,1,parent,null);
        mTasks.add(parent);
        mTasks.add(c1);
        mTasks.add(c2);
        /*mTasks.add(createTask("2", "2", true, 1, parent, null));
        mTasks.add(createTask("3", "3", true, 1, parent, null));*/
/*
        parent.addChildTask(parent,c1);
        parent.addChildTask(parent,c2);
*/

       /*
        Task parentTask = new Task();
        for (int i = 0; i < 20; i++) {
            Task task = new Task();
            if (i == 0 || i == 5 || i == 10 || i == 15) {
                parentTask = task;
            }
            task.setTitle("Task #" + i);
            task.setDescription("Task description #" + i);
            task.setDone(i % 2 == 0); //every other task will be done
            task.setPriority(i % 4);
            task.setParentTask(parentTask);
            //task.addChildTask(parentTask, task);


            mTasks.add(task);
        }
        */

    }

    public Task createTask(String title, String description, boolean done, int priority, Task parent, ArrayList<Task> children) {
        Task t = new Task();
        t.setTitle(title);
        t.setDescription(description);
        t.setDone(done);
        t.setPriority(priority);
        t.setParentTask(parent);
        t.setChildTasks(children);
        return t;
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
/*
    public Task getParentTask(UUID id) {
        Task t = getTask(id);
        return t.getParentTask();
    }
*/

}
