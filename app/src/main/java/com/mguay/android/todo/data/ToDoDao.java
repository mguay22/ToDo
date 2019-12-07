package com.mguay.android.todo.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ToDoDao {

    @Query("SELECT * FROM ToDo")
    LiveData<List<ToDo>> getAll();

    @Query("SELECT * FROM ToDo WHERE tid = :tid")
    int getToDoById(int tid);

    @Insert
    void insert(ToDo toDo);

    @Delete
    void delete(ToDo toDo);

    @Update
    void update(ToDo toDo);
}
