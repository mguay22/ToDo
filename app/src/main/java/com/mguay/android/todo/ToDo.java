package com.mguay.android.todo;

import java.util.Date;
import java.util.UUID;

enum Priority {
    A,
    B,
    C,
    D
}

public class ToDo {

    private boolean mIsComplete;
    private String mTitle;
    private Date mDueDate;
    private Priority mPriority;
    private UUID mToDoId;

    public ToDo(String title, Date dueDate, Priority priority) {
        mIsComplete = false;
        mTitle = title;
        mDueDate = dueDate;
        mPriority = priority;
        mToDoId = UUID.randomUUID();
    }

    public boolean isComplete() {
        return mIsComplete;
    }

    public void setComplete(boolean complete) {
        mIsComplete = complete;
    }

    public UUID getToDoId() {
        return mToDoId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDueDate() {
        return mDueDate;
    }

    public void setDueDate(Date dueDate) {
        mDueDate = dueDate;
    }

    public Priority getPriority() {
        return mPriority;
    }

    public void setPriority(Priority priority) {
        mPriority = priority;
    }
}
