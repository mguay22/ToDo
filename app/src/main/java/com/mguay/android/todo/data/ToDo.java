package com.mguay.android.todo.data;

import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity
@TypeConverters({DateConverter.class, PriorityConverter.class})
public class ToDo {

    public enum Priority {
        A,
        B,
        C,
        D
    }

    @PrimaryKey()
    public int tid;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "is_complete")
    public boolean isComplete;

    @ColumnInfo(name = "due_date", defaultValue = "CURRENT_TIMESTAMP")
    public Date dueDate;

    @ColumnInfo(name = "priority", defaultValue = "A")
    public Priority priority;


    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getPriority() {
        if (priority == null) {
            return "A";
        }
        return priority.name();
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}
