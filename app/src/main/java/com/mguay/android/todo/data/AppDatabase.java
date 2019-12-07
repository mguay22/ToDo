package com.mguay.android.todo.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ToDo.class}, version = 7)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase sAppDatabase;

    public static AppDatabase get(Context context) {
        if (sAppDatabase == null) {
            sAppDatabase = Room.databaseBuilder(context,
                    AppDatabase.class, "todos").fallbackToDestructiveMigration().build();
        }
        return sAppDatabase;
    }

    public abstract ToDoDao toDoDao();
}
