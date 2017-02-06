package ebozkurt.listtodo;

import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;


public class Task {

    private UUID mId;
    private String mTitle;
    private String mDescription;
    private boolean mDone;
    private int mPriority; //4 priority levels(low, medium, high, very high) = 0,1,2,3
    private Task mParentTask;
    private ArrayList<Task> mChildTasks;
    private int mLevel;
    // private boolean mHasChild, mHasParent;

    //add priority value (int)
    //sound, image, contact later

    public Task() {
        this(UUID.randomUUID());
        //mChildTasks = new ArrayList<Task>();
    }

    public Task(UUID id) {
        mId = id;
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public boolean isDone() {
        return mDone;
    }

    public void setDone(boolean done) {
        mDone = done;
    }

    public int getPriority() {
        return mPriority;
    }

    public void setPriority(int priority) {
        mPriority = priority;
    }

    public Task getParentTask() {
        return mParentTask;
    }

    public void setParentTask(Task parentTask) {
        mParentTask = parentTask;
    }

    public ArrayList<Task> getChildTasks() {
        return mChildTasks;
    }

    public void setChildTasks(ArrayList<Task> childTasks) {
        mChildTasks = childTasks;
    }
/*
    public void addChildTask(Task parent, Task child){
        //todo fix this function!!!
        ArrayList<Task> tasks = parent.getChildTasks();
            tasks.add(child);

    }
*/
    public int getLevel() {
        return mLevel;
    }

    public void setLevel(int level) {
        mLevel = level;
    }

    /*
    public int getLevel(Task task) {
        Task c = task;
        Task p = task.getParentTask();
        int level = 0;
        while ((c.getId() != p.getId())) {
            c = p;
            p = p.getParentTask();
            level++;
        }
        return level;
    }
    */
}