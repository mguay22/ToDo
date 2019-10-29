package com.mguay.android.todo;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

public class ToDoList {

    private static ToDoList sToDoList;
    private LinkedHashMap<UUID, ToDo> mToDos;

    public static ToDoList get(Context context) {
        if (sToDoList == null) {
            sToDoList = new ToDoList(context);
        }
        return sToDoList;
    }

    private ToDoList(Context context) {
        mToDos = new LinkedHashMap<>();

        ToDo toDo1 = new ToDo("To Do 1", new Date(), Priority.A);
        ToDo toDo2 = new ToDo("To Do 2", new Date(), Priority.B);
        ToDo toDo3 = new ToDo("To Do 3", new Date(), Priority.C);
        ToDo toDo4 = new ToDo("To Do 4", new Date(), Priority.D);
        ToDo toDo5 = new ToDo("To Do 5", new Date(), Priority.A);
        mToDos.put(toDo1.getToDoId(), toDo1);
        mToDos.put(toDo2.getToDoId(), toDo2);
        mToDos.put(toDo3.getToDoId(), toDo3);
        mToDos.put(toDo4.getToDoId(), toDo4);
        mToDos.put(toDo5.getToDoId(), toDo5);
    }

    public List<ToDo> getToDos() {
        return new ArrayList<>(mToDos.values());
    }

    public ToDo getToDo(UUID id) {
        return mToDos.get(id);
    }


}
