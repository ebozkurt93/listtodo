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
    private int mDepth;

    //sound, image, contact later

    public Task() {
        this(UUID.randomUUID());
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

    public ArrayList<Task> getChildTasks() {
        return mChildTasks;
    }

    public void setChildTasks(ArrayList<Task> childTasks) {
        mChildTasks = childTasks;
    }


    public void addChildTask(Task child) {
        if(mChildTasks == null) {
            mChildTasks = new ArrayList<Task>();
        }
        mChildTasks.add(child);
    }

    public void removeChildTask(Task removedChild){
        mChildTasks.remove(removedChild);
    }

    public Task getParentTask() {
        return mParentTask;
    }

    public void setParentTask(Task parentTask) {
        mParentTask = parentTask;
    }

    public int getDepth() {
        return mDepth;
    }

    public void setDepth(int depth) {
        mDepth = depth;
    }
}